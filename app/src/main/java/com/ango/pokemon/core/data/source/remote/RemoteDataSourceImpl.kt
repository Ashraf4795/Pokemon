package com.ango.pokemon.core.data.source.remote

import com.ango.pokemon.core.data.model.Pokemon
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.data.source.remote.retrofit.PokemonApiService
import com.ango.pokemon.core.utils.checkPokemonOffset

class RemoteDataSourceImpl(val pokemonApiService: PokemonApiService) : RemoteDataSourceContract {
    override suspend fun getPokemon(): Pokemon {
        return pokemonApiService.getPokemon()
    }

    override suspend fun getPokemonDetails(id: Long): PokemonDetails {
        return pokemonApiService.getPokemonDetails(id)
    }

    override suspend fun loadNextPage(offset: Int, limit: Int, count: Int): Pokemon {
        if (checkPokemonOffset(offset, limit, count)) {
            return pokemonApiService.loadNextPage(offset, limit)
        } else {
            return Pokemon()
        }
    }
}