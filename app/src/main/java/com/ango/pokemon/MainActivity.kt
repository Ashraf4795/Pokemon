package com.ango.pokemon

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ango.pokemon.core.utils.status_wrapper.Status
import com.ango.pokemon.feature.pokemon_main_screen.view_model.PokeMainScreenViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    val pokemonViewModel: PokeMainScreenViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initObserver()
    }

    private fun initObserver() {
        pokemonViewModel.getPokemon()
        pokemonViewModel.pokemon.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    Log.d(TAG, "loading")
                }
                Status.SUCCESS -> {
                    Log.d(TAG, "${it.data}")
                }
                else -> {
                    Log.d(TAG, "${it.message}")
                }
            }
        }
    }
}