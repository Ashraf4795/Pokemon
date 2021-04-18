package com.ango.pokemon.core.data.source.remote.retrofit

import com.ango.pokemon.core.app.BASE_POKEMON_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    //getter method to build retrofit instance
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_POKEMON_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val pokemonApiService: PokemonApiService = getRetrofit().create(PokemonApiService::class.java)
}