package com.ango.pokemon.feature.pokemon_main_screen.view

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.data.model.Type
import com.ango.pokemon.core.extenstion.loadImage
import com.ango.pokemon.databinding.PokemonItemBinding

class PokemonListAdapter(
    private val adapterPokemonDetailsCollection: MutableList<PokemonDetails>
) : RecyclerView.Adapter<PokemonListAdapter.PokemonDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonDetailsViewHolder {
        return PokemonDetailsViewHolder(
            PokemonItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonDetailsViewHolder, position: Int) {
        val pokemonAtPosition = adapterPokemonDetailsCollection[position]

        with(holder.pokemonDetailsBinding) {
            pokemonCardId.setOnClickListener { view ->
                navigateToDetailScreen(view, pokemonAtPosition)
            }
            imageView.loadImage(
                pokemonAtPosition.sprites?.other?.officialArtwork?.frontDefault ?: "", 50
            )
            pokemonNameId.text = pokemonAtPosition.name
            setTypes(this, pokemonAtPosition.types)
        }
    }

    private fun navigateToDetailScreen(view: View, pokemonAtPosition: PokemonDetails) {
        val goToPokemonDetailsAction =
            PokemonListFragmentDirections.actionPokemonListScreenToPokemonDetailsFragment(
                pokemonAtPosition.id ?: 1
            )
        view.findNavController().navigate(goToPokemonDetailsAction)
    }

    override fun getItemCount(): Int {
        return adapterPokemonDetailsCollection.size
    }

    private fun setTypes(pokemonItemBinding: PokemonItemBinding, types: List<Type>?) {
        if (types?.size == 1) {
            pokemonItemBinding.pokemonType1.visibility = VISIBLE
            pokemonItemBinding.pokemonType1.text = types.first().type?.name
        } else if (types?.size == 2) {

            pokemonItemBinding.pokemonType1.visibility = VISIBLE
            pokemonItemBinding.pokemonType1.text = types.first().type?.name

            pokemonItemBinding.pokemonType2.visibility = VISIBLE
            pokemonItemBinding.pokemonType2.text = types[1].type?.name

        } else if (types?.size == 3) {

            pokemonItemBinding.pokemonType1.visibility = VISIBLE
            pokemonItemBinding.pokemonType1.text = types.first().type?.name


            pokemonItemBinding.pokemonType2.visibility = VISIBLE
            pokemonItemBinding.pokemonType2.text = types[1].type?.name


            pokemonItemBinding.pokemonType3.visibility = VISIBLE
            pokemonItemBinding.pokemonType3.text = types[2].type?.name
        }
    }

    fun setPokemonDetailsList(pokemonDetailsList: MutableList<PokemonDetails>) {
        adapterPokemonDetailsCollection += pokemonDetailsList
        notifyDataSetChanged()
    }

    inner class PokemonDetailsViewHolder(val pokemonDetailsBinding: PokemonItemBinding) :
        RecyclerView.ViewHolder(pokemonDetailsBinding.root)
}

