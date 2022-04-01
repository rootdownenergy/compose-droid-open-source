package com.rootdown.dev.notesapp.root.feature_note.domain.use_case

import com.rootdown.dev.notesapp.root.feature_note.domain.model.CloudGroup
import com.rootdown.dev.notesapp.root.feature_note.domain.repository.CloudGroupRepo

class AddCloudGroup(
    private val repo: CloudGroupRepo
){
    suspend operator fun invoke(group: CloudGroup){
        repo.insertCloudGroup(group)
    }
}