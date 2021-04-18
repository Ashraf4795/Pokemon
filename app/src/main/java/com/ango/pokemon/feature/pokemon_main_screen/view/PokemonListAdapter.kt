package com.ango.pokemon.feature.pokemon_main_screen.view

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getColorStateList
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ango.pokemon.core.app.DEFAULT_COLOR
import com.ango.pokemon.core.app.pokemonColors
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.data.model.Type
import com.ango.pokemon.core.extenstion.loadImage
import com.ango.pokemon.databinding.PokemonItemBinding

class PokemonListAdapter(
    private val adapterPokemonDetailsCollection: MutableList<PokemonDetails>
) : RecyclerView.Adapter<PokemonListAdapter.PokemonDetailsViewHolder>() {

    //mutable object to hold search result.
    private var filteredPokemonDetailsList = mutableListOf<PokemonDetails>()

    //backup for @field adapterPokemonDetailsCollection, so that after filteration on adapter pokemon list
    //the original values are safe.
    private val backupPokemonDetailsList = adapterPokemonDetailsCollection.toMutableList()

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

        val speciesColor = pokemonAtPosition.species?.color?.name

        with(holder.pokemonDetailsBinding) {
            pokemonCardId.setOnClickListener { view ->
                navigateToDetailScreen(view, pokemonAtPosition)
            }

            pokemonCardId.backgroundTintList = getColorStateList(
                holder.pokemonDetailsBinding.root.context,
                pokemonColors.getValue(speciesColor ?: DEFAULT_COLOR).first
            )

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

    //set pokemon types
    //@param types: list of pokemon types like [fire, poison etc]
    private fun setTypes(pokemonItemBinding: PokemonItemBinding, types: List<Type>?) {
        if (types?.size == 1) {
            setPokemonType(pokemonItemBinding.pokemonType1, types.first().type?.name ?: "")
        } else if (types?.size == 2) {
            setPokemonType(pokemonItemBinding.pokemonType1, types.first().type?.name ?: "")
            setPokemonType(pokemonItemBinding.pokemonType2, types[1].type?.name ?: "")
        } else if (types?.size == 3) {
            setPokemonType(pokemonItemBinding.pokemonType1, types.first().type?.name ?: "")
            setPokemonType(pokemonItemBinding.pokemonType2, types[1].type?.name ?: "")
            setPokemonType(pokemonItemBinding.pokemonType3, types[2].type?.name ?: "")
        }
    }

    private fun setPokemonType(pokemonTypeTextView: TextView, pokemonTypeValue: String) {
        pokemonTypeTextView.text = pokemonTypeValue
        pokemonTypeTextView.visibility = VISIBLE
    }

    //set new list of pokemon details to adapter list
    fun setPokemonDetailsList(pokemonDetailsList: MutableList<PokemonDetails>) {
        val lastPosition = adapterPokemonDetailsCollection.size
        adapterPokemonDetailsCollection += pokemonDetailsList
        backupPokemonDetailsList += pokemonDetailsList

        //notify range of item change to avoid changing the all list
        //@field lastPosition is to get the last item in the current list as a starting index for change notification
        //@param adapterPokemonDetailsCollection.size is the updated size (current + new)
        // list as a last index for change notification
        notifyItemRangeChanged(lastPosition, adapterPokemonDetailsCollection.size)
    }

    //function to search for a pokemon with name containing @param query
    fun searchFor(query: String) {
        //first clear the previous list items from any previous filtration
        adapterPokemonDetailsCollection.clear()

        //add the backup list to get the original list of pokemons to filter from
        adapterPokemonDetailsCollection.addAll(backupPokemonDetailsList)

        //filter based on name
        filteredPokemonDetailsList =
            adapterPokemonDetailsCollection.filter { it.name?.contains(query) ?: false }
                .toMutableList()
        //before adding filtered items, clear all the items to add only filtered items to render it on screen
        adapterPokemonDetailsCollection.clear()
        adapterPokemonDetailsCollection.addAll(filteredPokemonDetailsList)
        notifyDataSetChanged()
    }

    inner class PokemonDetailsViewHolder(val pokemonDetailsBinding: PokemonItemBinding) :
        RecyclerView.ViewHolder(pokemonDetailsBinding.root)
}

