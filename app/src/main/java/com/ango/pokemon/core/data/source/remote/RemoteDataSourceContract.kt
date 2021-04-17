package com.ango.pokemon.core.data.source.remote

import com.ango.pokemon.core.data.model.Pokemon
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.data.model.Species

interface RemoteDataSourceContract {


    suspend fun getPokemon(): Pokemon
    suspend fun getPokemonDetails(id: Long): PokemonDetails
    suspend fun loadNextPage(offset: Int, limit: Int, count: Int): Pokemon
    suspend fun getPokemonSpecies(id: Long): Species

}