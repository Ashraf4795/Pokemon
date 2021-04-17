package com.ango.pokemon.feature.pokemon_details_screen.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.extenstion.loadImage
import com.ango.pokemon.core.utils.status_wrapper.Status
import com.ango.pokemon.databinding.FragmentPokemonDetailsBinding
import com.ango.pokemon.feature.pokemon_details_screen.view_model.PokemonDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PokemonDetailsFragment : Fragment() {
    val TAG = "PokemonDetailsFragment"

    private lateinit var pokemonDetailsBinding: FragmentPokemonDetailsBinding
    private val pokemonDetailsviewModel: PokemonDetailsViewModel by viewModel()

    private val pokemonDetailsArg: PokemonDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPokemonViewModelObserver()
    }

    private fun initPokemonViewModelObserver() {
        pokemonDetailsviewModel.getPokemonDetails(pokemonDetailsArg.pokemonDetailsId)
        pokemonDetailsviewModel.pokemonDetails.observe(viewLifecycleOwner) { state ->
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pokemonDetailsBinding = FragmentPokemonDetailsBinding.inflate(layoutInflater)
        return pokemonDetailsBinding.root
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PokemonDetailsFragment()
    }

    private fun updateUI(pokemonDetails: PokemonDetails) {
        with(pokemonDetailsBinding) {
            pokemonNameId.text = pokemonDetails.name
            pokemonIdNumberId.text = pokemonDetails.id.toString()
            pokemonDetails.getSpritesOfficialArtWork()?.let { url ->
                pokemonImgId.loadImage(url, 1)
            }

        }
    }
}