package com.rootdown.dev.notesapp.root.feature_note.presentation.add_edit_note

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rootdown.dev.notesapp.root.di.util.IoDispatcher
import com.rootdown.dev.notesapp.root.feature_note.data.local.NoteDao
import com.rootdown.dev.notesapp.root.feature_note.domain.model.*
import com.rootdown.dev.notesapp.root.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates
import kotlin.random.Random

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val dao: NoteDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private lateinit var currentNote: Note

    private val _stateNWCG = mutableStateOf<NotesWithCloudGroupState>(NotesWithCloudGroupState())
    val state: State<NotesWithCloudGroupState> = _stateNWCG

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter title..."
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter some content..."
    ))
    val noteContent: State<NoteTextFieldState> = _noteContent

    // must have initial value Note.noteColors.random().toArgb()
    private val _noteColor = mutableStateOf(Color.Green.toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId = -1
    private var cloudGroup = -1

    //keep track of current flow
    private var getNotesWithCloudGroupsJob: Job? = null

    init {
        Log.w("$$$", " init current note id: ${currentNoteId.toString()}")
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1){
                viewModelScope.launch {
                    Log.w("$*$", "in view model secondary constructor 1")
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNote = note
                        Log.w("$*$", "in view model. note: ${note.toString()}")
                        Log.w("$*$", "in view model note id: ${note.noteId.toString()}")
                        currentNoteId = note.noteId
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }

                }
            }
        }
        getNoteWithCloudGroups(currentNoteId)
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = _noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                    try {
                        if(currentNoteId == -1) {
                            if (cloudGroup != -1)
                            {
                                viewModelScope.launch(ioDispatcher) {
                                    noteUseCases.addNote(
                                        Note(
                                            title = noteTitle.value.text,
                                            content = noteContent.value.text,
                                            timestamp = System.currentTimeMillis(),
                                            color = noteColor.value,
                                            ii = cloudGroup
                                        )
                                    )
                                    _eventFlow.emit(UiEvent.SaveNote)

                                }

                                Log.w("$*$", "note if cloudGroup not -1")
                            }
                            else
                            {
                                viewModelScope.launch(ioDispatcher) {
                                    noteUseCases.addNote(
                                        Note(
                                            title = noteTitle.value.text,
                                            content = noteContent.value.text,
                                            timestamp = System.currentTimeMillis(),
                                            color = noteColor.value,
                                            ii = cloudGroup ?: -1
                                        )
                                    )
                                    _eventFlow.emit(UiEvent.SaveNote)

                                }
                                Log.w("$*$", "note if cloudGroup default(not selected) : ")
                            }

                        } else {
                            viewModelScope.launch {
                                Log.w("$*$", "update note : ${currentNote.toString()}")
                                Log.w("$*$", "update note : ${currentNote.noteId.toString()}")
                                //noteUseCases.editNote(currentNote)
                                val note = Note(
                                    noteId = currentNote.noteId,
                                    title = noteTitle.value.text,
                                    content = noteContent.value.text,
                                    timestamp = currentNote.timestamp,
                                    color = currentNote.color,
                                    ii = currentNote.ii
                                )
                                dao.update(note)
                                setCrossRef()
                                currentNoteId = currentNote.noteId
                                _eventFlow.emit(UiEvent.SaveNote)
                            }
                        }
                    } catch(e: InvalidNoteException){
                        viewModelScope.launch(ioDispatcher) {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = e.message ?: "Unknown Error"
                                )
                            )
                        }
                    }

            }
        }
    }
    private fun setCrossRef() {
        viewModelScope.launch(ioDispatcher) {
            //Log.w("$*$", "in setMediator")
            noteUseCases.addCrossRef(
                NoteCloudGroupCrossRef(
                    cloudGroupId = cloudGroup,
                    noteId = currentNoteId
                )
            )
        }
    }
    private fun getNoteWithCloudGroups(query: Int) {
        //val xix = CoroutineScope(SupervisorJob())
        getNotesWithCloudGroupsJob = noteUseCases.getNotesWithCloudGroups(query)
            .onEach { clouds ->
                _stateNWCG.value = state.value.copy(
                    noteWithCloudGroups = clouds
                )
            }
            .launchIn(viewModelScope)
    }
    fun setMediater(id: Int) {
        cloudGroup = id
        Log.w("$*$", "in setMediator id: ${cloudGroup.toString()}")
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}