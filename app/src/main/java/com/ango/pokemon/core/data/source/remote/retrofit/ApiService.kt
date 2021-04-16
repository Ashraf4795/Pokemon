package com.ango.pokemon.core.data.source.remote.retrofit

import com.ango.pokemon.core.data.model.Pokemon
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.data.model.Species
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {


    @GET("pokemon/")
    suspend fun getPokemon(): Pokemon

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: Long): PokemonDetails

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Long): Species


}