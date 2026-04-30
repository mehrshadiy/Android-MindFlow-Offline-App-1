package com.example.mindflow_offline_app.data.di

import android.content.Context
import androidx.room.Room
import com.example.mindflow_offline_app.data.db.ExerciseDao
import com.example.mindflow_offline_app.data.db.MoodDao
import com.example.mindflow_offline_app.data.db.TestResultDao
import com.example.mindflow_offline_app.repository.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "mindflow_db"
        ).build()

    @Provides
    fun provideMoodDao(db: AppDatabase): MoodDao = db.moodDao()

    @Provides
    fun provideExerciseDao(db: AppDatabase): ExerciseDao = db.exerciseDao()

    @Provides
    fun provideTestResultDao(db: AppDatabase): TestResultDao = db.testResultDao()
}
