package com.example.competitiontable.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.competitiontable.R
import com.example.competitiontable.databinding.ActivityMainBinding
import com.example.competitiontable.presentation.model.CompetitionTableState
import com.example.competitiontable.presentation.recycler.base.CommonAdapter
import com.example.competitiontable.presentation.recycler.scoreTableDelegate
import com.example.competitiontable.presentation.recycler.scoreTableMiddleCellDelegate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: CompetitionTableViewModel by viewModel()

    private val scoreTableAdapter = CommonAdapter(
        scoreTableMiddleCellDelegate(),
        scoreTableDelegate { id, text ->
            viewModel.onSellTextChanged(id, text)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.scoreRecycler.layoutManager = GridLayoutManager(this, 7)
        binding.scoreRecycler.adapter = scoreTableAdapter

        viewModel.state.onEach { state -> renderState(state) }.launchIn(lifecycleScope)
    }

    private fun renderState(state: CompetitionTableState) = with(binding) {
        scoreTableAdapter.items = state.scoreSellItems

        if (!scoreRecycler.isComputingLayout) {
            scoreTableAdapter.notifyDataSetChanged()
        }

        if (!state.isScoreCorrect) {
            Toast.makeText(this@MainActivity, getString(R.string.toast_messaage), Toast.LENGTH_SHORT).show()
        }
    }
}