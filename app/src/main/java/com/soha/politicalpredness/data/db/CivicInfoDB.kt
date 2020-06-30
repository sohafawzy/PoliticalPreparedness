package com.soha.politicalpredness.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.soha.politicalpredness.models.Election

@Database(entities = [Election::class], version = 1, exportSchema = false)
abstract class CivicInfoDB : RoomDatabase() {
    abstract fun civicInfoDao(): CivicInfoDao

    companion object {
        private var INSTANCE: CivicInfoDB? = null

        fun getDatabase(context: Context): CivicInfoDB {
            val instance = INSTANCE
            if (instance != null) return instance
            synchronized(this) {
                val inst = Room.databaseBuilder(context, CivicInfoDB::class.java, "CivicInfoDB")
                    .fallbackToDestructiveMigration().build()
                INSTANCE = inst
                return inst
            }
        }
    }

}