package com.ango.pokemon.feature.pokemon_main_screen.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ango.pokemon.R
import com.ango.pokemon.core.utils.status_wrapper.Status
import com.ango.pokemon.databinding.FragmentPokemonListScreenBinding
import com.ango.pokemon.feature.pokemon_main_screen.view_model.PokemonListViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel


class PokemonListFragment : Fragment() {
    private val TAG = "PokemonListScreen"

    private lateinit var pokemonListBinding: FragmentPokemonListScreenBinding
    private val pokemonViewModel: PokemonListViewModel by viewModel()
    private lateinit var pokemonRecyclerView: RecyclerView
    private val pokemonListAdapter: PokemonListAdapter = PokemonListAdapter(mutableListOf())

    private lateinit var loadMoreSnackBar: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pokemonListBinding = FragmentPokemonListScreenBinding.inflate(layoutInflater)
        initClickListeners()
        initPokemonRecyclerView()
        initObserver()
        return pokemonListBinding.root
    }


    private fun initClickListeners() {
        pokemonListBinding.loadMoreBtnId.setOnClickListener {
            it.visibility = INVISIBLE
            pokemonViewModel.nextPokemonPage()
        }
        pokemonListBinding.searchInputId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(searchQuery: Editable) {
                val query = searchQuery.toString().trim()
                pokemonListAdapter.searchFor(query)
            }

        })
    }


    private fun initPokemonRecyclerView() {
        pokemonRecyclerView = pokemonListBinding.pokemonRvId
        pokemonRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        pokemonRecyclerView.adapter = pokemonListAdapter
        pokemonRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //check the layoutmanager item count to see if the current item is the last one
                val grideLayoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = grideLayoutManager.itemCount
                val lastItemVisible = grideLayoutManager.findLastVisibleItemPosition()

                //if the total items less than or equal the last visible item, then
                //the scrolling reaches the bottom
                //@param dy is a variable to indicate the value of scrolling vertically in the Y axis
                //if @param dy is > zero then the scrolling direction is down.
                if (totalItemCount <= (lastItemVisible + 1) && dy > 0) {
                    loadMoreSnackBar =
                        Snackbar.make(recyclerView, R.string.load_more, Snackbar.LENGTH_LONG)
                    loadMoreSnackBar.setAction(R.string.load_more_action_text) {
                        pokemonViewModel.nextPokemonPage()
                        loadMoreSnackBar.dismiss()
                    }
                    loadMoreSnackBar.show()
                }
            }
        })
    }


    private fun initObserver() {
        initPokemonObserver()
        initPokemonDetailsObserver()
    }

    private fun initPokemonObserver() {
        pokemonViewModel.pokemon.observe(viewLifecycleOwner) { pokemonState ->
            when (pokemonState.status) {
                Status.LOADING -> {
                    pokemonListBinding.pokemonLoaderId.visibility = VISIBLE
                    Log.d(TAG, "loading")
                }
                Status.SUCCESS -> {
                    pokemonListBinding.pokemonLoaderId.visibility = INVISIBLE
                    Log.d(TAG, "${pokemonState.data}")
                    pokemonState.data?.let { pokemon ->
                        pokemon.results?.let { pokemonResult ->
                            pokemonViewModel.getPokemonDetails(pokemonResult)
                        }
                    }
                }
                else -> {
                    Log.d(TAG, "${pokemonState.message}")
                }
            }
        }
    }

    private fun initPokemonDetailsObserver() {
        pokemonViewModel.pokemonDetails.observe(viewLifecycleOwner) { pokemonDetailsState ->
            when (pokemonDetailsState.status) {
                Status.LOADING -> {
                    pokemonListBinding.pokemonLoaderId.visibility = VISIBLE
                    Log.d(TAG, "loading")
                }
                Status.SUCCESS -> {
                    pokemonListBinding.pokemonLoaderId.visibility = INVISIBLE
                    Log.d(TAG, "${pokemonDetailsState.data}")
                    pokemonDetailsState.data?.let { pokemonList ->
                        pokemonListAdapter.setPokemonDetailsList(pokemonList)
                    }
                }
                else -> {
                    Log.d(TAG, "${pokemonDetailsState.message}")
                }
            }
        }
    }
}