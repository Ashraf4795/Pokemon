package com.ango.pokemon.core.repository

import com.ango.pokemon.core.data.model.Pokemon
import com.ango.pokemon.core.data.model.PokemonDetails

interface Repository {

    suspend fun getPokemon(): Pokemon
    suspend fun getPokemonDetails(id: Long): PokemonDetails

}