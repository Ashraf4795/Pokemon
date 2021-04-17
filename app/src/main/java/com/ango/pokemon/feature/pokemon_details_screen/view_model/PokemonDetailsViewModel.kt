package com.ango.pokemon.feature.pokemon_details_screen.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.ango.pokemon.core.base.BaseViewModel
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.repository.Repository
import com.ango.pokemon.core.utils.status_wrapper.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class PokemonDetailsViewModel(
    private val repo: Repository,
    private val IO: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel(repo, IO) {

    private val _pokemonDetails = MutableLiveData<State<PokemonDetails>>()
    val pokemonDetails: LiveData<State<PokemonDetails>> = _pokemonDetails


    fun getPokemonDetails(id: Long) = liveData {
        emit(State.loading())

        val pokemonDetails = repo.getPokemonDetails(id)
        pokemonDetails.setPokemonSpecies(repo.getPokemonSpecies(id))
        _pokemonDetails.postValue(State.success(pokemonDetails))

        emit(State.success(pokemonDetails))
    }
}