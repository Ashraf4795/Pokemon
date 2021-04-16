package com.ango.pokemon.feature.pokemon_main_screen.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ango.pokemon.R
import com.ango.pokemon.core.utils.status_wrapper.Status
import com.ango.pokemon.feature.pokemon_main_screen.view_model.PokeMainScreenViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class PokemonListScreen : Fragment() {
    private val TAG = "PokemonListScreen"

    private val pokemonViewModel: PokeMainScreenViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pokemon_list_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        pokemonViewModel.getPokemon()
        pokemonViewModel.pokemon.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    Log.d(TAG, "loading")
                }
                Status.SUCCESS -> {
                    Log.d(TAG, "${it.data}")
                    it.data?.let { pokemon ->
                        pokemonViewModel.getPokemonDetails(pokemon)
                    }
                }
                else -> {
                    Log.d(TAG, "${it.message}")
                }
            }
        }

        pokemonViewModel.pokemonDetails.observe(viewLifecycleOwner) {
            //give list to main screen list adapter
        }
    }
}