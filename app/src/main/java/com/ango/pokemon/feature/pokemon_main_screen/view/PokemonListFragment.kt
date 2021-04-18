package com.ango.pokemon.feature.pokemon_main_screen.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ango.pokemon.core.utils.status_wrapper.Status
import com.ango.pokemon.databinding.FragmentPokemonListScreenBinding
import com.ango.pokemon.feature.pokemon_main_screen.view_model.PokemonListViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class PokemonListFragment : Fragment() {
    private val TAG = "PokemonListScreen"

    private lateinit var pokemonListBinding: FragmentPokemonListScreenBinding
    private val pokemonViewModel: PokemonListViewModel by viewModel()
    private lateinit var pokemonRecyclerView: RecyclerView
    private val pokemonListAdapter: PokemonListAdapter = PokemonListAdapter(mutableListOf())
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
                    if (pokemonListBinding.loadMoreBtnId.visibility != VISIBLE) {
                        pokemonListBinding.loadMoreBtnId.visibility = VISIBLE
                        Toast.makeText(requireActivity(), "visible", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
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