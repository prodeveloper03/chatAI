package com.code.machinecoding.di

import android.content.Context
import androidx.room.Room
import com.code.machinecoding.data.local.ChatDatabase
import com.code.machinecoding.data.local.MessageDao
import com.code.machinecoding.data.repository.ChatRepository
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
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ChatDatabase =
        Room.databaseBuilder(
            context,
            ChatDatabase::class.java,
            "chat_db"
        ).build()

    @Provides
    fun provideMessageDao(db: ChatDatabase): MessageDao =
        db.messageDao()



}