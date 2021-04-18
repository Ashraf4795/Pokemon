package com.ango.pokemon.feature.pokemon_main_screen.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ango.pokemon.core.data.model.Pokemon
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.data.model.Result
import com.ango.pokemon.core.extenstion.SingleLiveEvent
import com.ango.pokemon.core.repository.Repository
import com.ango.pokemon.core.utils.status_wrapper.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonListViewModel(
    val repository: Repository,
    val IO: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val TAG = "PokemonListViewModel"
    private val _pokemonDetails = SingleLiveEvent<State<MutableList<PokemonDetails>>>()
    val pokemonDetails: LiveData<State<MutableList<PokemonDetails>>> = _pokemonDetails

    private val _pokemon = SingleLiveEvent<State<Pokemon>>()
    val pokemon: LiveData<State<Pokemon>> = _pokemon


    init {
        getPokemon()
    }

    fun getPokemon() {
        viewModelScope.launch(IO) {
            _pokemon.postValue(State.loading())
            val pokemon = repository.getPokemon()
            _pokemon.postValue(State.success(pokemon))
        }
    }

    fun getPokemonDetails(listOfPokemonResult: List<Result>) {
        viewModelScope.launch(IO) {
            _pokemonDetails.postValue(State.loading())
            val pokemonDetailsCollection = mutableListOf<PokemonDetails>()
            listOfPokemonResult.forEach { result ->
                result.url?.let { url ->
                    val pokemonDetails = repository.getPokemonDetailsByUrl(url)
                    pokemonDetailsCollection.add(pokemonDetails)
                }
            }
            _pokemonDetails.postValue(State.success(pokemonDetailsCollection))
            Log.d(TAG, "Combined list")
        }
    }

    fun nextPokemonPage() {
        viewModelScope.launch(IO) {
            val nextPokemonPageUrl = _pokemon.value?.data?.next
            _pokemon.postValue(State.loading())
            val pokemon =
                nextPokemonPageUrl?.let { nextPage -> repository.nextPokemonPage(nextPage) }
            _pokemon.postValue(State.success(pokemon))
        }
    }
}