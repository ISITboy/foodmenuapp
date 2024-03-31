package com.example.gosporttastingtask.ui.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gosporttastingtask.ui.domain.models.ModelProduct

@Database(entities = [ModelProduct::class],version = 1,)
@TypeConverters(Converter::class)
abstract class Database : RoomDatabase(){
    abstract val productsDao: ProductsDao
}