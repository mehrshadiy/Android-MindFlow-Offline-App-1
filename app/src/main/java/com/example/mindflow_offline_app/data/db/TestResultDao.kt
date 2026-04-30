package com.example.mindflow_offline_app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TestResultDao {
    @Insert
    suspend fun insert(result: TestResult)

    @Query("SELECT * FROM test_result ORDER BY date DESC")
    suspend fun getAllResults(): List<TestResult>
}