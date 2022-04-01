package com.rootdown.dev.notesapp.root.feature_note.domain.use_case

import com.rootdown.dev.notesapp.root.feature_note.domain.model.CloudGroup
import com.rootdown.dev.notesapp.root.feature_note.domain.repository.CloudGroupRepo
import com.rootdown.dev.notesapp.root.feature_note.domain.util.CloudGroupOrder
import com.rootdown.dev.notesapp.root.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCloudGroup(
    private val repo: CloudGroupRepo
) {
    // flow operator fun
    // should only have 1 public fun
    // make class act like a fun with operator invoke
    operator fun invoke(
        cloudGroupOrder: CloudGroupOrder = CloudGroupOrder.Date(OrderType.Descending)
    ): Flow<List<CloudGroup>> {
        //get cloudgroup, sort cloudgroup
        return repo.getCloudGroup().map { cloudgroup ->
            when(cloudGroupOrder.orderType){
                is OrderType.Ascending -> {
                    when(cloudGroupOrder){
                        is CloudGroupOrder.Title -> cloudgroup.sortedBy { it.title.lowercase() }
                        is CloudGroupOrder.Color -> cloudgroup.sortedBy { it.timestamp }
                        is CloudGroupOrder.Date -> cloudgroup.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(cloudGroupOrder){
                        is CloudGroupOrder.Title -> cloudgroup.sortedByDescending { it.title.lowercase() }
                        is CloudGroupOrder.Color -> cloudgroup.sortedByDescending { it.timestamp }
                        is CloudGroupOrder.Date -> cloudgroup.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}