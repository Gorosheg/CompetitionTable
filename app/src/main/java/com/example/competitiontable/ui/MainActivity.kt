package com.example.competitiontable.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.competitiontable.R
import com.example.competitiontable.databinding.ActivityMainBinding
import com.example.competitiontable.presentation.CompetitionTableViewModel
import com.example.competitiontable.presentation.model.CompetitionTableState
import com.example.competitiontable.ui.recycler.base.CommonAdapter
import com.example.competitiontable.ui.recycler.tableDelegate
import com.example.competitiontable.ui.recycler.tableMiddleCellDelegate
import com.example.competitiontable.presentation.utils.buildCountableCell
import com.example.competitiontable.presentation.utils.buildCounter
import com.example.competitiontable.presentation.utils.buildParticipants
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: CompetitionTableViewModel by viewModel()

    private val tableAdapter = CommonAdapter(
        tableMiddleCellDelegate(),
        tableDelegate { id, text ->
            viewModel.onSellTextChanged(id, text)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(binding) {
            val scoreRecyclerLayoutManager = object : GridLayoutManager(this@MainActivity, 7) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            tableRecycler.layoutManager = scoreRecyclerLayoutManager
            tableRecycler.adapter = tableAdapter

            viewModel.state.onEach(::renderState).launchIn(lifecycleScope)

            verticalCounterLayout.buildCounter()
            horizontalCounterLayout.buildCounter()
            participantsLayout.buildParticipants()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderState(state: CompetitionTableState) = with(binding) {
        tableAdapter.items = state.scoreCellItems

        if (!tableRecycler.isComputingLayout) {
            tableAdapter.notifyDataSetChanged()
        }

        renderTotalScore(state)
        renderWinners(state)

        if (!state.isScoreCorrect) {
            Toast.makeText(this@MainActivity, getString(R.string.toast_messaage), Toast.LENGTH_SHORT).show()
        }
    }

    private fun renderTotalScore(state: CompetitionTableState) = with(binding) {
        totalScoreLayout.removeAllViews()

        state.totalScoreItems.forEach { totalScoreItem ->
            totalScoreLayout.buildCountableCell(
                text = totalScoreItem.score,
                width = 300
            )
        }
    }

    private fun renderWinners(state: CompetitionTableState) = with(binding) {
        winnersLayout.removeAllViews()

        state.winnerItems.forEach { winnerItem ->
            winnersLayout.buildCountableCell(
                text = winnerItem.place,
                width = 300
            )
        }
    }
}