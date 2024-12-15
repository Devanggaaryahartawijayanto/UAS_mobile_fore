package com.example.uas_mobile.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CoffeDao {

    @Query("SELECT * FROM coffe_table ORDER BY id ASC")
    fun getAllCoffe(): LiveData<List<CoffeEntity>>

    @Query("SELECT * FROM coffe_table WHERE id = :id LIMIT 1")
    fun getCoffeeById(id: Int): CoffeEntity?

    @Query("SELECT COUNT(*) > 0 FROM coffe_table WHERE id = :id")
    fun isCoffeeInCart(id: Int): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coffee: CoffeEntity)

    @Delete
    fun delete(coffee: CoffeEntity)
}