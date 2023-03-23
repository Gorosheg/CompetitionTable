package com.example.competitiontable.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.competitiontable.presentation.model.CompetitionTableState
import com.example.competitiontable.presentation.model.ScoreSellItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CompetitionTableViewModel : ViewModel() {

    val state = MutableStateFlow(CompetitionTableState())

    init {
        setState()
    }

    fun onSellTextChanged(id: Int, newScore: String) {
        when (newScore) {
            "0", "1", "2", "3", "4", "5" -> {
                val newTable = buildNewTable(id, newScore, true)
                setNewSellScore(id, newScore, newTable)
            }
            "" -> Unit
            else -> {
                val newTable = buildNewTable(id, newScore, false)
                state.update { CompetitionTableState(scoreSellItems = newTable, isScoreCorrect = false) }
            }
        }

    }

    private fun buildNewTable(id: Int, newScore: String, isScoreCorrect: Boolean): MutableList<ScoreSellItem> {
        val newTable = state.value.scoreSellItems.toMutableList()
        newTable[id] = ScoreSellItem(id, newScore, isScoreCorrect)
        return newTable
    }

    private fun setNewSellScore(id: Int, newScore: String, newTable: MutableList<ScoreSellItem>) {
        val sameScoredId = getSameScoredId(id, newTable.size)
        newTable[sameScoredId] = ScoreSellItem(sameScoredId, newScore)

        state.update { CompetitionTableState(scoreSellItems = newTable, isScoreCorrect = true) }
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

    private fun setState() {
        viewModelScope.launch {
            state.update {
                CompetitionTableState(
                    scoreSellItems = getInitScoreSells()
                )
            }
        }
    }

    private fun getInitScoreSells(): List<ScoreSellItem> {
        val list = mutableListOf<ScoreSellItem>()
        for (i in 0 until TABLE_LENGTH * TABLE_LENGTH) {
            list.add(ScoreSellItem(id = i, score = ""))
        }
        return list
    }

    companion object {
        private const val TABLE_LENGTH = 7
    }
}