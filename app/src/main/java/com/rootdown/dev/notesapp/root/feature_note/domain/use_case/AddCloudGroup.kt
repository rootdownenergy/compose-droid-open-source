package com.rootdown.dev.notesapp.root.feature_note.domain.use_case

import com.rootdown.dev.notesapp.root.feature_note.domain.model.CloudGroup
import com.rootdown.dev.notesapp.root.feature_note.data.repo.CloudGroupRepoImpl
import javax.inject.Inject

class AddCloudGroup @Inject constructor(
    private val repo: CloudGroupRepoImpl
){
    suspend operator fun invoke(group: CloudGroup){
        repo.insertCloudGroup(group)
    }
}