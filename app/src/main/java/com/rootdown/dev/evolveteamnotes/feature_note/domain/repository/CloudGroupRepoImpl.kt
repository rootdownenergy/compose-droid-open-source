package com.rootdown.dev.notesapp.root.feature_note.domain.repository

import kotlinx.coroutines.flow.Flow

class CloudGroupRepoImpl(
    private val dao: com.rootdown.dev.notesapp.root.feature_note.data.data_source.CloudGroupDao
): CloudGroupRepo {
    override fun getCloudGroup(): Flow<List<com.rootdown.dev.notesapp.root.feature_note.domain.model.CloudGroup>> {
        return dao.getCloudGroup()
    }

    override suspend fun getCloudGroupById(id: Int): com.rootdown.dev.notesapp.root.feature_note.domain.model.CloudGroup? {
        return dao.getCloudGroupById(id)
    }

    override suspend fun insertCloudGroup(group: com.rootdown.dev.notesapp.root.feature_note.domain.model.CloudGroup) {
        dao.insertCloudGroup(group)
    }

    override suspend fun deleteCloudGroup(group: com.rootdown.dev.notesapp.root.feature_note.domain.model.CloudGroup) {
        dao.deleteCloudGroup(group)
    }
}
