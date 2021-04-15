package com.ango.pokemon.core.data.source.local

import com.ango.pokemon.core.data.source.local.room_database.PokemonDao

class LocalDataSourceImpl(val pokemonDao: PokemonDao) : LocalDataSourceContract {
}