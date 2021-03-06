package com.rootdown.dev.notesapp.root.feature_note.domain.use_case

import com.rootdown.dev.notesapp.root.feature_note.domain.model.CloudGroup
import com.rootdown.dev.notesapp.root.feature_note.data.repo.CloudGroupRepoImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCloudGroup @Inject constructor(
    private val repo: CloudGroupRepoImpl
) {
    // flow operator fun
    // should only have 1 public fun
    // make class act like a fun with operator invoke
    operator fun invoke(): Flow<List<CloudGroup>> {
        //get cloudgroup, sort cloudgroup
        return repo.getCloudGroup()
    }
}