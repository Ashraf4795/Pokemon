package com.ango.pokemon.core.data.source.local.room_database

import androidx.room.RoomDatabase


//@Database()
abstract class AppDataBase : RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao
}