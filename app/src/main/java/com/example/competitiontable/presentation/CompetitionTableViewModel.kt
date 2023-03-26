package com.example.competitiontable.presentation

import androidx.lifecycle.ViewModel
import com.example.competitiontable.presentation.builder.*
import com.example.competitiontable.presentation.model.*
import com.example.competitiontable.presentation.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CompetitionTableViewModel : ViewModel() {

    val state = MutableStateFlow(
        CompetitionTableState(
            scoreCellItems = buildEmptyCells(),
            totalScoreItems = buildEmptyTotalScoreItems(),
            winnerItems = buildEmptyWinnerItems()
        )
    )

    fun onSellTextChanged(id: Int, score: String) {
        when (score) {
            "", "0", "1", "2", "3", "4", "5" -> showCorrectScore(id, score)
            else -> showIncorrectScore(id, score)
        }
    }

    private fun showCorrectScore(id: Int, score: String) {
        state.update { state ->
            buildCorrectState(
                scoreSellItems = state.scoreCellItems,
                totalScoreItems = state.totalScoreItems,
                id = id,
                score = score
            )
        }
    }

    private fun showIncorrectScore(id: Int, score: String) {
        state.update { state ->
            val newTable = buildTable(
                scoreSellItems = state.scoreCellItems,
                id = id,
                newScore = score,
                isScoreCorrect = false
            )

            newTable.leaveCellFocus(id)
            state.copy(scoreCellItems = newTable, isScoreCorrect = false)
        }
    }

    companion object {
        const val TABLE_LENGTH = 7
    }
}