package com.example.gosporttastingtask.ui.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gosporttastingtask.ui.Constants.TABLE_NAME
import com.example.gosporttastingtask.ui.domain.models.ModelProduct
import kotlinx.coroutines.flow.Flow
@Dao
interface ProductsDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllProducts(): Flow<List<ModelProduct>>

    @Query("SELECT category FROM $TABLE_NAME")
    fun getCategories(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: ModelProduct)

    @Query("SELECT * FROM $TABLE_NAME where category LIKE :category")
    fun getProductsByCategory(category:String): Flow<List<ModelProduct>>
}