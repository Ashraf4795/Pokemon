package com.ango.pokemon.feature.pokemon_details_screen.view.pokemon_details_pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ango.pokemon.R
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.utils.status_wrapper.Status
import com.ango.pokemon.databinding.FragmentPokemonAboutBinding
import com.ango.pokemon.feature.pokemon_details_screen.view_model.PokemonDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PokemonAboutFragment : Fragment() {
    val TAG = "About_fragment"
    private val pokemonDetailsViewModel: PokemonDetailsViewModel by viewModel()
    private lateinit var aboutBinding: FragmentPokemonAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aboutBinding = FragmentPokemonAboutBinding.inflate(layoutInflater)
        return aboutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemonDetailsViewModel.pokemonDetails.observe(viewLifecycleOwner) { state ->
            when (state.status) {
                Status.LOADING -> {
                    Log.d(TAG, "loading")
                }
                Status.SUCCESS -> {
                    state.data?.let { pokemonDetails ->
                        updatePokemonAboutUI(pokemonDetails)
                    }
                }
                else -> {
                    Log.d(TAG, "${state.message}")
                }
            }
        }

    }

    private fun updatePokemonAboutUI(pokemonDetails: PokemonDetails) {
        with(aboutBinding) {
            //calculate weight from hectograms to gram (1 hectograms equal 100 grams)
            val weight = pokemonDetails.weight?.times(100L).toString() + " lbs"

            //calculate height from decimetres to centimetres (1 decimetres equal 10 centimetres)
            val height = pokemonDetails.height?.times(10L).toString() + " cm"
            weightValueId.text = weight
            heightValueId.text = height
            abilitiesValueId.text =
                pokemonDetails.abilities?.map { it.ability?.name }?.joinToString()
            eggGroupValueId.text = pokemonDetails.species?.eggGroup?.map { it.name }?.joinToString()
            isDefaultValueId.text = if (pokemonDetails.isDefault == true) {
                getString(R.string.yes)
            } else {
                getString(R.string.no)
            }

        }
    }
}

