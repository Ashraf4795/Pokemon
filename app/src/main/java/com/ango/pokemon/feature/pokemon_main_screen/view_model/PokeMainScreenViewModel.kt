package com.ango.pokemon.feature.pokemon_main_screen.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ango.pokemon.core.data.model.Pokemon
import com.ango.pokemon.core.repository.Repository
import com.ango.pokemon.core.utils.status_wrapper.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokeMainScreenViewModel(
        val repository: Repository,
        val IO: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _pokemon = MutableLiveData<State<Pokemon>>()
    val pokemon: LiveData<State<Pokemon>> = _pokemon


    fun getPokemon() {
        viewModelScope.launch(IO) {
            _pokemon.postValue(State.loading(null))
            val pokemon = repository.getPokemon()
            _pokemon.postValue(State.success(pokemon))
        }
    }

}