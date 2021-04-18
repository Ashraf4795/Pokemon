package com.ango.pokemon.core.app

import android.app.Application
import com.ango.pokemon.core.di.coreModule
import com.ango.pokemon.feature.pokemon_details_screen.di.pokemonDetailsModule
import com.ango.pokemon.feature.pokemon_main_screen.di.mainScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokemonApp : Application() {
    val TAG: String = "Pokemon App tag"

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PokemonApp)
            modules(
                coreModule,
                mainScreenModule,
                pokemonDetailsModule,
            )
        }
    }
}