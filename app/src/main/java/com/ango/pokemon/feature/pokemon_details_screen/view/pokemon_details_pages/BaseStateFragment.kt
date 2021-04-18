package com.ango.pokemon.feature.pokemon_details_screen.view.pokemon_details_pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ango.pokemon.core.app.*
import com.ango.pokemon.core.data.model.PokemonDetails
import com.ango.pokemon.core.utils.status_wrapper.Status
import com.ango.pokemon.databinding.FragmentBaseStateBinding
import com.ango.pokemon.feature.pokemon_details_screen.view_model.PokemonDetailsViewModel
import com.google.android.material.progressindicator.LinearProgressIndicator
import org.koin.android.viewmodel.ext.android.viewModel


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
                    Log.d(TAG, "loading")
                }
                Status.SUCCESS -> {
                    state.data?.let { pokemonDetails ->
                        updateBaseStatUI(pokemonDetails)
                    }
                }
                else -> {
                    Log.d(TAG, "${state.message}")
                }
            }
        }

    }

    private fun updateBaseStatUI(pokemonDetails: PokemonDetails) {
        //total power of pokemon state
        var totalBaseStat: Long = 0

        pokemonDetails.stats?.forEach { statItem ->
            when (statItem.stat?.name) {
                HP -> {
                    updateStateView(
                        baseStateBinding.hpNumberId,
                        baseStateBinding.hpProgressId,
                        statItem.baseStat ?: 0L
                    )
                    totalBaseStat += statItem.baseStat ?: 0
                }
                ATTACK -> {
                    updateStateView(
                        baseStateBinding.attackNumberId,
                        baseStateBinding.attackProgressId,
                        statItem.baseStat ?: 0L
                    )
                    totalBaseStat += statItem.baseStat ?: 0
                }
                DEFENSE -> {
                    updateStateView(
                        baseStateBinding.defenseNumberId,
                        baseStateBinding.defenseProgressId,
                        statItem.baseStat ?: 0L
                    )
                    totalBaseStat += statItem.baseStat ?: 0
                }
                SPECIAL_ATTACK -> {
                    updateStateView(
                        baseStateBinding.spAttackNumberId,
                        baseStateBinding.spAttackProgressId,
                        statItem.baseStat ?: 0L
                    )
                    totalBaseStat += statItem.baseStat ?: 0
                }
                SPECIAL_DEFENSE -> {
                    updateStateView(
                        baseStateBinding.spDefenseNumberId,
                        baseStateBinding.spDefenseProgressId,
                        statItem.baseStat ?: 0L
                    )
                    totalBaseStat += statItem.baseStat ?: 0
                }
                SPEED -> {
                    updateStateView(
                        baseStateBinding.speedNumberId,
                        baseStateBinding.speedProgressId,
                        statItem.baseStat ?: 0L
                    )
                    totalBaseStat += statItem.baseStat ?: 0
                }
            }
        }
        //Calculate the total state power percentage to update total progress bar
        updateTotalState(baseStateBinding, totalBaseStat)
    }

    //update total pokemon state value and progress percentage value
    private fun updateTotalState(baseStateBinding: FragmentBaseStateBinding, totalBaseStat: Long) {
        with(baseStateBinding) {
            //total power divided by (number of stat * 100 as a full power)
            // multiply by 100 to calc percentage
            val totalProgress = ((totalBaseStat / TOTAL_STATE_FULL_POWER) * 100).toLong()
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

    //update pokemon stat with base stat number and progress value
    private fun updateStateView(
        stateNumber: TextView,
        stateProgress: LinearProgressIndicator,
        baseStat: Long
    ) {
        stateNumber.text = baseStat.toString()
        stateProgress.setIndicatorColor(
            ContextCompat.getColor(
                requireContext(),
                getProgressColor(baseStat ?: 0).first
            )
        )
        stateProgress.progress = baseStat?.toInt() ?: 0
    }

    //helper function to deterimne progress color
    //if base state < 50 color will be red || else color will be green
    private fun getProgressColor(baseStat: Long): Pair<Int, Int> {
        if (baseStat < 50) return pokemonColors.getValue("red")
        else return pokemonColors.getValue("green")
    }

}