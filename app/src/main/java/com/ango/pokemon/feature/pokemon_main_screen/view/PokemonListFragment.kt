package com.ango.pokemon.feature.pokemon_main_screen.view

import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ango.pokemon.core.utils.status_wrapper.Status
import com.ango.pokemon.databinding.FragmentPokemonListScreenBinding
import com.ango.pokemon.feature.pokemon_main_screen.view_model.PokeMainScreenViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class PokemonListFragment : Fragment() {
    private val TAG = "PokemonListScreen"

    private lateinit var binding: FragmentPokemonListScreenBinding
    private val pokemonViewModel: PokeMainScreenViewModel by viewModel()
    private lateinit var pokemonRecyclerView: RecyclerView
    private lateinit var pokemonListAdapter: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonListScreenBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPokemonRecyclerView()
        initObserver()

    }

    private fun initPokemonRecyclerView() {
        pokemonRecyclerView = binding.pokemonRvId
        pokemonRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        pokemonListAdapter = PokemonListAdapter(mutableListOf(), requireContext())
        pokemonRecyclerView.adapter = pokemonListAdapter
        pokemonRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(LayoutDirection.LTR)) {
                    pokemonViewModel.getPokemonDetails()
                }
            }
        })
    }

    private fun initObserver() {
        initPokemonObserver()
        initPokemonDetailsObserver()
    }

    private fun initPokemonObserver() {
        pokemonViewModel.getPokemon()
        pokemonViewModel.pokemon.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.pokemonLoaderId.visibility = VISIBLE
                    Log.d(TAG, "loading")
                }
                Status.SUCCESS -> {
                    binding.pokemonLoaderId.visibility = INVISIBLE
                    Log.d(TAG, "${it.data}")
                    it.data?.let { pokemon ->
                        pokemonViewModel.getPokemonDetails()
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
                    binding.pokemonLoaderId.visibility = VISIBLE
                    Log.d(TAG, "loading")
                }
                Status.SUCCESS -> {
                    binding.pokemonLoaderId.visibility = INVISIBLE
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