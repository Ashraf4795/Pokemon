package com.ango.pokemon.core.app

import android.app.Application
import android.util.Log

class PokemonApp : Application() {
    val TAG: String = "Pokemon App tag"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "PokemonApp created")
    }
}