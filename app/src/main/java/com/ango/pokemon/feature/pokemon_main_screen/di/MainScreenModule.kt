package com.ango.pokemon.feature.pokemon_main_screen.di

import com.ango.pokemon.feature.pokemon_main_screen.view_model.PokemonListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainScreenModule = module {
    viewModel { PokemonListViewModel(get()) }
}