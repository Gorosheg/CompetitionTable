package com.example.competitiontable.presentation

import androidx.lifecycle.ViewModel
import com.example.competitiontable.presentation.model.*
import com.example.competitiontable.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CompetitionTableViewModel : ViewModel() {

    val state = MutableStateFlow(CompetitionTableState())

    init {
        setState()
    }

    fun onSellTextChanged(id: Int, newScore: String) {
        when (newScore) {
            "", "0", "1", "2", "3", "4", "5" -> {
                val newTable = buildNewTable(
                    scoreSellItems = state.value.scoreSellItems,
                    id = id,
                    newScore = newScore,
                    isScoreCorrect = true
                )

                setNewSellScore(id, newScore, newTable)
            }
            else -> {
                val newTable = buildNewTable(
                    scoreSellItems = state.value.scoreSellItems,
                    id = id,
                    newScore = newScore,
                    isScoreCorrect = false
                )

                newTable.leaveCellFocus(id)

                state.update {
                    state.value.copy(scoreSellItems = newTable, isScoreCorrect = false)
                }
            }
        }

    }

    private fun setNewSellScore(id: Int, newScore: String, newTable: MutableList<TableListItem>) {
        val sameScoredId = getSameScoredId(id, newTable.size)
        val firstLineIndex = id % TABLE_LENGTH
        val secondLineIndex = sameScoredId % TABLE_LENGTH

        newTable[sameScoredId] = ScoreSellItem(id = sameScoredId, score = newScore)

        if (newScore.isBlank()) newTable.leaveCellFocus(id)
        else newTable.changeCellFocus(id)

        updateTableResultsInState(firstLineIndex, secondLineIndex, newTable)
    }

    private fun getSameScoredId(id: Int, tableSize: Int): Int {
        for (sameScoreId in 0..tableSize) {
            if (sameScoreId % TABLE_LENGTH == id / TABLE_LENGTH
                && sameScoreId / TABLE_LENGTH == id % TABLE_LENGTH
            ) {
                return sameScoreId
            }
        }
        return 0
    }

    private fun updateTableResultsInState(
        firstLineIndex: Int,
        secondLineIndex: Int,
        newTable: MutableList<TableListItem>,
    ) {
        state.update {
            val totalScoreItems = it.totalScoreItems
                .toMutableList()
                .apply {
                    this[firstLineIndex] = TotalScoreItem(newTable.countLineTotalScore(firstLineIndex))
                    this[secondLineIndex] = TotalScoreItem(newTable.countLineTotalScore(secondLineIndex))
                }

            val winnerItems = totalScoreItems.countWinnerPlaces()

            CompetitionTableState(
                scoreSellItems = newTable,
                isScoreCorrect = true,
                totalScoreItems = totalScoreItems,
                winnerItems = winnerItems
            )
        }
    }

    private fun MutableList<TableListItem>.changeCellFocus(id: Int) {
        val nextId = id + 1
        val nextItem = this[nextId]

        this.forEach {
            if (it is ScoreSellItem) {
                this[it.id] = (this[it.id] as ScoreSellItem).copy(isFocused = false)
            }
        }

        if (nextId == TABLE_LENGTH * TABLE_LENGTH) return

        if (nextItem is ScoreMiddleItem || nextItem is ScoreSellItem && nextItem.score.isNotEmpty()) {
            changeCellFocus(nextId)
        } else {
            this[nextId] = (nextItem as ScoreSellItem).copy(isFocused = true)
        }
    }

    private fun MutableList<TableListItem>.leaveCellFocus(id: Int) {
        this.forEach {
            if (it is ScoreSellItem) {
                this[it.id] = (this[it.id] as ScoreSellItem).copy(isFocused = false)
            }
        }

        if (this[id] is ScoreSellItem) {
            this[id] = (this[id] as ScoreSellItem).copy(isFocused = true)
        }
    }

    private fun setState() {
        state.update {
            CompetitionTableState(
                scoreSellItems = buildEmptyTableCells(),
                totalScoreItems = buildEmptyTotalScoreCells(),
                winnerItems = buildEmptyWinnersCells()
            )
        }
    }

    companion object {
        const val TABLE_LENGTH = 7
    }
}