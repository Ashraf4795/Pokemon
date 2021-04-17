package com.ango.pokemon.feature.pokemon_details_screen.di

import com.ango.pokemon.feature.pokemon_details_screen.view_model.PokemonDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokemonDetailsModule = module {
    viewModel { PokemonDetailsViewModel(get()) }
}