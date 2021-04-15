package com.ango.pokemon.core.data.source.local.room_database

import android.content.Context
import androidx.room.Room
import com.ango.pokemon.core.app.DATABASE_NAME

object DatabaseBuilder {
    private lateinit var INSTANCE: AppDataBase

    fun getInstance(context: Context): AppDataBase {
        if (!this::INSTANCE.isInitialized) {
            synchronized(AppDataBase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE
    }

    private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    DATABASE_NAME
            ).build()


}