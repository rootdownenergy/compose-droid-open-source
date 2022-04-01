package com.rootdown.dev.notesapp.root.feature_note.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rootdown.dev.notesapp.root.feature_note.presentation.theme.*


@Entity(
    tableName = "cloud_group"
)
data class CloudGroup(
    val groupName: String = "default",
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val xx: String,
){
    @PrimaryKey(autoGenerate = true)
    var cloudGroupId: Int = 0
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}
