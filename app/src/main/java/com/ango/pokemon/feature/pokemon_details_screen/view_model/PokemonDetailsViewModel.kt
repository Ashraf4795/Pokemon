package com.ango.pokemon.feature.pokemon_details_screen.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ango.pokemon.core.base.BaseViewModel
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.repository.Repository
import com.ango.pokemon.core.utils.status_wrapper.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val repo: Repository,
    private val IO: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel(repo, IO) {

    private val _pokemonDetails = MutableLiveData<State<PokemonDetails>>()
    val pokemonDetails: LiveData<State<PokemonDetails>> = _pokemonDetails


    fun getPokemonDetails(id: Long) {
        viewModelScope.launch(IO) {
            _pokemonDetails.postValue(State.loading())
            val pokemonDetails = repo.getPokemonDetails(id)
            _pokemonDetails.postValue(State.success(pokemonDetails))
        }
    }
}