package com.soha.politicalpredness.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soha.politicalpredness.models.Election

@Dao
interface CivicInfoDao {
    @Query("SELECT * FROM election")
    fun getSavedElection(): LiveData<List<Election>>

    @Query("SELECT * FROM election WHERE id = :electionId")
    suspend fun getElection(electionId: Int): Election?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun followElection(election: Election)

    @Query("DELETE FROM election where id == :electionId")
    fun unFollowElection(electionId: Int)
}