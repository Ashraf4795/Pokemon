package com.ango.pokemon.feature.pokemon_main_screen.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
    }

    private fun initPokemonRecyclerView() {
        pokemonRecyclerView = pokemonListBinding.pokemonRvId
        pokemonRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        pokemonRecyclerView.adapter = pokemonListAdapter
        pokemonRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = linearLayoutManager.itemCount
                val lastItemVisible = linearLayoutManager.findLastVisibleItemPosition()
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

    private fun buildLoadMoreSnackBar(view: View): Snackbar {
        val snackBar = Snackbar.make(view, "Load More", Snackbar.LENGTH_LONG)
        snackBar.setAction("YES") {
            pokemonViewModel.nextPokemonPage()
            snackBar.dismiss()
        }
        return snackBar
    }

    private fun initObserver() {
        initPokemonObserver()
        initPokemonDetailsObserver()
    }

    private fun initPokemonObserver() {
        pokemonViewModel.pokemon.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    pokemonListBinding.pokemonLoaderId.visibility = VISIBLE
                    Log.d(TAG, "loading")
                }
                Status.SUCCESS -> {
                    pokemonListBinding.pokemonLoaderId.visibility = INVISIBLE
                    Log.d(TAG, "${it.data}")
                    it.data?.let { pokemon ->
                        pokemon.results?.let { pokemonResult ->
                            pokemonViewModel.getPokemonDetails(
                                pokemonResult
                            )
                        }
                    }
                }
                else -> {
                    Log.d(TAG, "${it.message}")
                }
            }
        }
    }

    private fun initPokemonDetailsObserver() {
        pokemonViewModel.pokemonDetails.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    pokemonListBinding.pokemonLoaderId.visibility = VISIBLE
                    Log.d(TAG, "loading")
                }
                Status.SUCCESS -> {
                    pokemonListBinding.pokemonLoaderId.visibility = INVISIBLE
                    Log.d(TAG, "${it.data}")
                    it.data?.let { pokemonList ->
                        pokemonListAdapter.setPokemonDetailsList(pokemonList)
                    }
                }
                else -> {
                    Log.d(TAG, "${it.message}")
                }
            }
        }
    }
}