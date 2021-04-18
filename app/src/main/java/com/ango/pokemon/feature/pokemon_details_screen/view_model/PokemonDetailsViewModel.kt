package com.ango.pokemon.feature.pokemon_details_screen.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.repository.Repository
import com.ango.pokemon.core.utils.status_wrapper.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class PokemonDetailsViewModel(
    private val repository: Repository,
    private val IO: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _pokemonDetails = MutableLiveData<State<PokemonDetails>>()
    val pokemonDetails: LiveData<State<PokemonDetails>> = _pokemonDetails


    //use lifecycle live data to emit new value based on @param id
    //@param id: pokemon object id
    fun getPokemonDetails(id: Long) = liveData(IO) {
        emit(State.loading())

        val pokemonDetails = repository.getPokemonDetails(id)
        pokemonDetails.setPokemonSpecies(repository.getPokemonSpecies(id))
        _pokemonDetails.postValue(State.success(pokemonDetails))

        emit(State.success(pokemonDetails))
    }
}