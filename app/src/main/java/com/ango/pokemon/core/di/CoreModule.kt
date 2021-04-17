package com.ango.pokemon.core.di

import com.ango.pokemon.core.app.PokemonApp
import com.ango.pokemon.core.data.source.local.LocalDataSourceContract
import com.ango.pokemon.core.data.source.local.LocalDataSourceImpl
import com.ango.pokemon.core.data.source.remote.RemoteDataSourceContract
import com.ango.pokemon.core.data.source.remote.RemoteDataSourceImpl
import com.ango.pokemon.core.data.source.remote.retrofit.RetrofitBuilder
import com.ango.pokemon.core.repository.Repository
import com.ango.pokemon.core.repository.RepositoryImpl
import org.koin.dsl.module

val coreModule = module {

    single { RetrofitBuilder.pokemonApiService }
    single<RemoteDataSourceContract> { RemoteDataSourceImpl(get()) }

    //single {DatabaseBuilder.getInstance(androidContext()).getPokemonDao()}
    single<LocalDataSourceContract> { LocalDataSourceImpl() }

    single<Repository> { RepositoryImpl(get(), get()) }

    single { PokemonApp() }
}