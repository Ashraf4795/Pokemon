package com.ango.pokemon.feature.pokemon_details_screen.view.pokemon_details_pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.utils.status_wrapper.Status
import com.ango.pokemon.databinding.FragmentPokemonAboutBinding
import com.ango.pokemon.feature.pokemon_details_screen.view_model.PokemonDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

private const val POKEMON_DETAILS = "pokemon_details_PokemonAboutFragment"


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
                    //binding.pokemonLoaderId.visibility = View.VISIBLE
                    Log.d(TAG, "loading")
                }
                Status.SUCCESS -> {
                    //binding.pokemonLoaderId.visibility = View.INVISIBLE
                    state.data?.let { pokemonDetails ->
                        updateUI(pokemonDetails)
                    }
                }
                else -> {
                    Log.d(TAG, "${state.message}")
                }
            }
        }

    }

    private fun updateUI(pokemonDetails: PokemonDetails) {
        with(aboutBinding) {
            weightValueId.text = pokemonDetails.weight.toString()
            heightValueId.text = pokemonDetails.height.toString()
            abilitiesValueId.text =
                pokemonDetails.abilities?.map { it.ability?.name }?.joinToString()
            eggGroupValueId.text = pokemonDetails.species?.eggGroup?.map { it.name }?.joinToString()
            isDefaultValueId.text = pokemonDetails.isDefault?.toString()
        }
    }
}

