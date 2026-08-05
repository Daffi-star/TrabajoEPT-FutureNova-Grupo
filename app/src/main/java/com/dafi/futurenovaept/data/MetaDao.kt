package com.dafi.futurenovaept.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MetaDao {
    @Query("SELECT COUNT(*) FROM metas_table")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeta(meta: MetaEntity)

    @Query("DELETE FROM metas_table WHERE id = :metaId")
    suspend fun deleteMetaById(metaId: String)
}