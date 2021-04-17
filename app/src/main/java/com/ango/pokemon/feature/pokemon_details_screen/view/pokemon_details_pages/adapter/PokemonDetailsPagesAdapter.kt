package com.ango.pokemon.feature.pokemon_details_screen.view.pokemon_details_pages.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ango.pokemon.core.app.POKEMON_DETAILS_NUM_PAGES
import com.ango.pokemon.feature.pokemon_details_screen.view.pokemon_details_pages.PokemonAboutFragment

class PokemonDetailsPagesAdapter(hostFragment: FragmentActivity) :
    FragmentStateAdapter(hostFragment) {

    private val pokemonDetailsPages = mapOf<Int, Fragment>(
        1 to PokemonAboutFragment(),

        )

    override fun getItemCount(): Int = POKEMON_DETAILS_NUM_PAGES

    override fun createFragment(position: Int): Fragment =
        pokemonDetailsPages.getValue(position + 1)
}