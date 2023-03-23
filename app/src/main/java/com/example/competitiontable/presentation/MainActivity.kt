package com.example.competitiontable.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.competitiontable.R
import com.example.competitiontable.databinding.ActivityMainBinding
import com.example.competitiontable.presentation.model.CompetitionTableState
import com.example.competitiontable.presentation.recycler.base.CommonAdapter
import com.example.competitiontable.presentation.recycler.scoreTableDelegate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: CompetitionTableViewModel by viewModel()

    private val scorecheetAdapter = CommonAdapter(
        scoreTableDelegate()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.scoreRecycler.layoutManager = GridLayoutManager(this, 7)
        binding.scoreRecycler.adapter = scorecheetAdapter

        viewModel.state.onEach { state -> renderState(state) }.launchIn(lifecycleScope)
    }

    private fun renderState(state: CompetitionTableState) = with(binding) {
        scorecheetAdapter.items = state.scoreSellItems
        scorecheetAdapter.notifyDataSetChanged()
    }
}