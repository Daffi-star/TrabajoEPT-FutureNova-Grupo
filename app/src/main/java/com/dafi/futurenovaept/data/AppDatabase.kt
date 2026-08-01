package com.dafi.futurenovaept.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DiagnosisRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diagnosisDao(): DiagnosisDao
}