package com.ango.pokemon.feature.pokemon_details_screen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ango.pokemon.databinding.FragmentPokemonDetailsBinding

class PokemonDetailsFragment : Fragment() {

    private lateinit var pokemonDetailsBinding: FragmentPokemonDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
}