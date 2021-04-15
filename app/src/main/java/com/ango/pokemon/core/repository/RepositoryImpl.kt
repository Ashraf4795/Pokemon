package com.ango.pokemon.core.repository

import com.ango.pokemon.core.data.model.Pokemon
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.data.source.local.LocalDataSourceContract
import com.ango.pokemon.core.data.source.remote.RemoteDataSourceContract

class RepositoryImpl(
        val localDataSource: LocalDataSourceContract,
        val remoteDataSource: RemoteDataSourceContract) : Repository {
    override suspend fun getPokemon(): Pokemon {
        return remoteDataSource.getPokemon()
    }

    override suspend fun getPokemonDetails(id: Long): PokemonDetails {
        return remoteDataSource.getPokemonDetails(id)
    }

}