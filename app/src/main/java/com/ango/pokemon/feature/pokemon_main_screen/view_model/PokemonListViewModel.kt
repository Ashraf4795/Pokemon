package com.ango.pokemon.feature.pokemon_main_screen.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ango.pokemon.core.app.ALL_POKEMON_LOADED
import com.ango.pokemon.core.app.LOAD_LIMIT
import com.ango.pokemon.core.base.BaseViewModel
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.repository.Repository
import com.ango.pokemon.core.utils.status_wrapper.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonListViewModel(
    val repository: Repository,
    val IO: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel(repository, IO) {

    private val _pokemonDetails = MutableLiveData<State<MutableList<PokemonDetails>>>()
    val pokemonDetails: LiveData<State<MutableList<PokemonDetails>>> = _pokemonDetails

    fun getPokemonDetails() {
        viewModelScope.launch(IO) {
            val pokemonDetailsCollection = mutableListOf<PokemonDetails>()
            pokemonCount.let { pokemonCount ->
                _pokemonDetails.postValue(State.loading())
                if (pokemonLoadOffset + LOAD_LIMIT <= pokemonCount) {
                    for (next in 1..LOAD_LIMIT) {
                        val pokemonDetail = repository.getPokemonDetails(pokemonLoadOffset)
                        pokemonDetail.setPokemonSpecies(
                            repository.getPokemonSpecies(
                                pokemonDetail.id ?: 1
                            )
                        )
                        pokemonLoadOffset += 1
                        pokemonDetailsCollection.add(pokemonDetail)
                    }
                    _pokemonDetails.postValue(State.success(pokemonDetailsCollection))
                } else {
                    showMessage(ALL_POKEMON_LOADED)
                }

            }

        }
    }
}