package com.example.gosporttastingtask.ui.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gosporttastingtask.ui.Constants.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class ModelProduct(
    @PrimaryKey(autoGenerate = true) val id :Int,
    val name :String,
    val category:String,
    val image:String,
    val ingredients:List<String>
)
