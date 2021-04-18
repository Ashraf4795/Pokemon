package com.ango.pokemon.core.data.source.remote

import com.ango.pokemon.core.data.model.Pokemon
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.data.model.Species
import com.ango.pokemon.core.data.source.remote.retrofit.PokemonApiService

class RemoteDataSourceImpl(val pokemonApiService: PokemonApiService) : RemoteDataSourceContract {
    override suspend fun getPokemon(): Pokemon {
        return pokemonApiService.getPokemon()
    }

    override suspend fun getPokemonDetails(id: Long): PokemonDetails {
        return pokemonApiService.getPokemonDetails(id)
    }


    override suspend fun getPokemonSpecies(id: Long): Species {
        return pokemonApiService.getPokemonSpecies(id)
    }

    override suspend fun getPokemonDetailsByUrl(url: String): PokemonDetails {
        return pokemonApiService.getPokemonDetailsByUrl(url)
    }

    override suspend fun nextPokemonPage(nextPokemonPageUrl: String): Pokemon {
        return pokemonApiService.nextPokemonPage(nextPokemonPageUrl)
    }
}