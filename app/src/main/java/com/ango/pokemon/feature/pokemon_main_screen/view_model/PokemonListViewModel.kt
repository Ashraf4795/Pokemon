package com.ango.pokemon.feature.pokemon_main_screen.view_model

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
    private val repository: Repository,
    val IO: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val TAG = "PokemonListViewModel"

    private val _pokemonDetails = SingleLiveEvent<State<MutableList<PokemonDetails>>>()
    val pokemonDetails: LiveData<State<MutableList<PokemonDetails>>> = _pokemonDetails

    private val _pokemon = SingleLiveEvent<State<Pokemon>>()
    val pokemon: LiveData<State<Pokemon>> = _pokemon


    init {
        getPokemon()
    }

    private fun getPokemon() {
        viewModelScope.launch(IO) {
            _pokemon.postValue(State.loading())
            val pokemon = repository.getPokemon()
            _pokemon.postValue(State.success(pokemon))
        }
    }

    //@param listOfPokemonResult is a list of object consist of pokemon name and url
    // url is to get pokemon details
    fun getPokemonDetails(listOfPokemonResult: List<Result>) {
        viewModelScope.launch(IO) {
            _pokemonDetails.postValue(State.loading())
            val pokemonDetailsCollection = mutableListOf<PokemonDetails>()

            //loop through the list to retrieve pokemonDetails and species object for each pokemon
            listOfPokemonResult.forEach { result ->
                result.url?.let { url ->
                    val pokemonDetails = repository.getPokemonDetailsByUrl(url)
                    pokemonDetails.setPokemonSpecies(
                        repository.getPokemonSpecies(pokemonDetails.id ?: 1)
                    )
                    pokemonDetailsCollection.add(pokemonDetails)
                }
            }
            _pokemonDetails.postValue(State.success(pokemonDetailsCollection))
        }
    }

    //get the next page of pokemons with pokemon.next url
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