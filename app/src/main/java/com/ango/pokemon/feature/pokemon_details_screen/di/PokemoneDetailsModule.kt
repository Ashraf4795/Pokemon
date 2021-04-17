package com.ango.pokemon.feature.pokemon_details_screen.di

import com.ango.pokemon.feature.pokemon_details_screen.view_model.PokemonDetailsViewModel
import org.koin.dsl.module

val pokemonDetailsModule = module {
    single { PokemonDetailsViewModel(get()) }
}