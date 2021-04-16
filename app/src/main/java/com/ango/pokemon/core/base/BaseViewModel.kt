package com.ango.pokemon.core.base

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

open class BaseViewModel(
    val mRepository: Repository,
    val mIO: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    var pokemonCount: Long = 0
    private val _showMessage = MutableLiveData<String>()
    val showMessage: LiveData<String> = _showMessage

    private val _pokemon = MutableLiveData<State<Pokemon>>()
    val pokemon: LiveData<State<Pokemon>> = _pokemon

    companion object {
        var pokemonLoadOffset: Long = 1
    }

    init {
        getPokemon()
    }


    fun getPokemon() {
        viewModelScope.launch(mIO) {
            _pokemon.postValue(State.loading())
            val pokemon = mRepository.getPokemon()
            pokemon.count?.let { count ->
                pokemonCount = count
            }
            _pokemon.postValue(State.success(pokemon))
        }
    }

    fun showMessage(message: String) {
        _showMessage.postValue(message)
    }

}