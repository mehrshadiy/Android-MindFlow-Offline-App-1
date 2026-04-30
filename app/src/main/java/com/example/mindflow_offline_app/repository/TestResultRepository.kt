package com.example.mindflow_offline_app.repository

import com.example.mindflow_offline_app.data.db.TestResult
import com.example.mindflow_offline_app.data.db.TestResultDao
import javax.inject.Inject

class TestResultRepository @Inject constructor(private val dao: TestResultDao) {
    suspend fun insert(result: TestResult) = dao.insert(result)
    suspend fun getAllResults(): List<TestResult> = dao.getAllResults()
}