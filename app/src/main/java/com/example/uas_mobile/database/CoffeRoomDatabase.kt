package com.example.uas_mobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CoffeEntity::class], version = 1, exportSchema = false)
abstract class CoffeRoomDatabase : RoomDatabase() {
    abstract fun coffeDao(): CoffeDao?

    companion object{
        @Volatile
        private var INSTANCE: CoffeRoomDatabase? = null

        fun getDatabase(context: Context): CoffeRoomDatabase?{
            if (INSTANCE == null) {
                synchronized(CoffeRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CoffeRoomDatabase::class.java,
                        "coffe_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}