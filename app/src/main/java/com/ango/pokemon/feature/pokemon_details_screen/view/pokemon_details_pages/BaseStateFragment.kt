package com.ango.pokemon.feature.pokemon_details_screen.view.pokemon_details_pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ango.pokemon.core.app.pokemonColors
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.utils.status_wrapper.Status
import com.ango.pokemon.databinding.FragmentBaseStateBinding
import com.ango.pokemon.feature.pokemon_details_screen.view_model.PokemonDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class BaseStateFragment : Fragment() {

    private val TAG = "BaseStateFragment"
    private val pokemonDetailsViewModel: PokemonDetailsViewModel by viewModel()
    private lateinit var baseStateBinding: FragmentBaseStateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        baseStateBinding = FragmentBaseStateBinding.inflate(inflater)
        return baseStateBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemonDetailsViewModel.pokemonDetails.observe(viewLifecycleOwner) { state ->
            when (state.status) {
                Status.LOADING -> {
                    //binding.pokemonLoaderId.visibility = View.VISIBLE
                    Log.d(TAG, "loading")
                }
                Status.SUCCESS -> {
                    //binding.pokemonLoaderId.visibility = View.INVISIBLE
                    state.data?.let { pokemonDetails ->
                        updateUI(pokemonDetails)
                    }
                }
                else -> {
                    Log.d(TAG, "${state.message}")
                }
            }
        }

    }

    private fun updateUI(pokemonDetails: PokemonDetails) {
        var totalBaseStat: Long = 0
        with(baseStateBinding) {
            pokemonDetails.stats?.forEach { statItem ->
                when (statItem.stat?.name) {
                    "hp" -> {
                        hpNumberId.text = statItem.baseStat.toString()
                        hpProgressId.setIndicatorColor(
                            ContextCompat.getColor(
                                requireContext(),
                                getProgressColor(statItem.baseStat ?: 0).first
                            )
                        )
                        hpProgressId.progress = statItem.baseStat?.toInt() ?: 0
                        totalBaseStat += statItem.baseStat ?: 0
                    }
                    "attack" -> {
                        attackNumberId.text = statItem.baseStat.toString()
                        attackProgressId.setIndicatorColor(
                            ContextCompat.getColor(
                                requireContext(),
                                getProgressColor(statItem.baseStat ?: 0).first
                            )
                        )
                        attackProgressId.progress = statItem.baseStat?.toInt() ?: 0
                        totalBaseStat += statItem.baseStat ?: 0
                    }
                    "defense" -> {
                        defenseNumberId.text = statItem.baseStat.toString()
                        defenseProgressId.setIndicatorColor(
                            ContextCompat.getColor(
                                requireContext(),
                                getProgressColor(statItem.baseStat ?: 0).first
                            )
                        )
                        defenseProgressId.progress = statItem.baseStat?.toInt() ?: 0
                        totalBaseStat += statItem.baseStat ?: 0
                    }
                    "special-attack" -> {
                        spAttackNumberId.text = statItem.baseStat.toString()
                        spAttackProgressId.setIndicatorColor(
                            ContextCompat.getColor(
                                requireContext(),
                                getProgressColor(statItem.baseStat ?: 0).first
                            )
                        )
                        spAttackProgressId.progress = statItem.baseStat?.toInt() ?: 0
                        totalBaseStat += statItem.baseStat ?: 0
                    }
                    "special-defense" -> {
                        spDefenseNumberId.text = statItem.baseStat.toString()
                        spDefenseProgressId.setIndicatorColor(
                            ContextCompat.getColor(
                                requireContext(),
                                getProgressColor(statItem.baseStat ?: 0).first
                            )
                        )
                        spDefenseProgressId.progress = statItem.baseStat?.toInt() ?: 0
                        totalBaseStat += statItem.baseStat ?: 0
                    }
                    "speed" -> {
                        speedNumberId.text = statItem.baseStat.toString()
                        speedProgressId.setIndicatorColor(
                            ContextCompat.getColor(
                                requireContext(),
                                getProgressColor(statItem.baseStat ?: 0).first
                            )
                        )
                        speedProgressId.progress = statItem.baseStat?.toInt() ?: 0
                        totalBaseStat += statItem.baseStat ?: 0
                    }
                }
            }
            val totalProgress = ((totalBaseStat / 600f) * 100).toLong()
            totalNumberId.text = totalBaseStat.toString()
            totalProgressId.setIndicatorColor(
                ContextCompat.getColor(
                    requireContext(),
                    getProgressColor(totalProgress).first
                )
            )
            totalProgressId.progress = totalProgress.toInt()
        }
    }

    private fun getProgressColor(baseStat: Long): Pair<Int, Int> {
        if (baseStat < 50) return pokemonColors.getValue("red")
        else return pokemonColors.getValue("green")
    }

}