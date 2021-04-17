package com.ango.pokemon.core.repository

import com.ango.pokemon.core.data.model.Pokemon
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.data.model.Species

interface Repository {

    suspend fun getPokemon(): Pokemon
    suspend fun getPokemonDetails(id: Long): PokemonDetails
    suspend fun getPokemonSpecies(id: Long): Species
}