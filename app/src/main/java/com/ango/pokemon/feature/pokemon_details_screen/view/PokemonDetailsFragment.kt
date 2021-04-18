package com.ango.pokemon.feature.pokemon_details_screen.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.ango.pokemon.R
import com.ango.pokemon.core.app.DEFAULT_COLOR
import com.ango.pokemon.core.app.pagesNames
import com.ango.pokemon.core.app.pokemonColors
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.extenstion.loadImage
import com.ango.pokemon.core.utils.resolveColor
import com.ango.pokemon.core.utils.status_wrapper.Status
import com.ango.pokemon.databinding.FragmentPokemonDetailsBinding
import com.ango.pokemon.feature.pokemon_details_screen.view.pokemon_details_pages.adapter.PokemonDetailsPagesAdapter
import com.ango.pokemon.feature.pokemon_details_screen.view_model.PokemonDetailsViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.viewmodel.ext.android.viewModel


class PokemonDetailsFragment : Fragment() {

    val TAG = "PokemonDetailsFragment"

    private lateinit var pokemonDetailsBinding: FragmentPokemonDetailsBinding
    private val pokemonDetailsViewModel: PokemonDetailsViewModel by viewModel()

    private val pokemonDetailsArg: PokemonDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonDetailsPagerAdapter = PokemonDetailsPagesAdapter(requireActivity())
        pokemonDetailsBinding.pokemonDetailsVpId.adapter = pokemonDetailsPagerAdapter

        val tabLayout = pokemonDetailsBinding.pokemonDetailsPagesTabId
        TabLayoutMediator(tabLayout, pokemonDetailsBinding.pokemonDetailsVpId) { tab, position ->
            tab.text = getString(pagesNames.getValue(position))
        }.attach()

        initPokemonViewModelObserver()
    }

    private fun initPokemonViewModelObserver() {
        pokemonDetailsViewModel.getPokemonDetails(pokemonDetailsArg.pokemonDetailsId)
            .observe(viewLifecycleOwner) { state ->
                when (state.status) {
                    Status.LOADING -> {
                        Log.d(TAG, "loading")
                    }
                    Status.SUCCESS -> {
                        state.data?.let { pokemonDetails ->
                            updatePokemonDetailsUI(pokemonDetails)
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
        setClickListeners()
        return pokemonDetailsBinding.root
    }

    private fun setClickListeners() {
        pokemonDetailsBinding.pokemonDetailsBackId.setOnClickListener { view ->
            view.findNavController().navigateUp()
        }
    }


    private fun updatePokemonDetailsUI(pokemonDetails: PokemonDetails) {
        val speciesColor = pokemonDetails.species?.color?.name ?: DEFAULT_COLOR
        val isWhite = (speciesColor == "white")
        //change text views color to black if the pokemon species color is white
        //@field speciesColor is used to set background color value
        if (isWhite) {
            changeUIColorToBlack()
        }

        //update details page UI values
        with(pokemonDetailsBinding) {
            pokemonDetailsRootId.setBackgroundResource(pokemonColors.getValue(speciesColor).first)
            pokemonNameId.text = pokemonDetails.name
            pokemonIdNumberId.text = "#" + pokemonDetails.id.toString()

            pokemonDetails.getSpritesOfficialArtWork()?.let { url ->
                pokemonImgId.loadImage(url, 1)
            }

            pokemonDetails.types?.map { it.type?.name }?.forEach { typeName ->
                val typeChip = Chip(requireContext())
                typeChip.text = typeName
                typeChip.chipBackgroundColor = resolveColor(requireContext(), speciesColor, false)
                if (isWhite) {
                    typeChip.setTextColor(getColorStateList(requireContext(), R.color.black))
                }
                pokemonTypeChipGroupId.addView(typeChip)
            }

        }
    }

    //change Text views in details page to black
    private fun changeUIColorToBlack() {
        val resourceColor = R.color.black
        with(pokemonDetailsBinding) {
            pokemonNameId.setTextColor(getColorStateList(requireContext(), resourceColor))
            pokemonNameId.setTextColor(getColorStateList(requireContext(), resourceColor))
        }
    }
}