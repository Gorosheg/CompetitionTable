package com.example.competitiontable.presentation

import androidx.lifecycle.ViewModel
import com.example.competitiontable.presentation.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CompetitionTableViewModel : ViewModel() {

    val state = MutableStateFlow(CompetitionTableState())

    init {
        setState()
    }

    fun onSellTextChanged(id: Int, newScore: String) {
        when (newScore) {
            "0", "1", "2", "3", "4", "5" -> {
                val newTable = buildNewTable(id = id, newScore = newScore, isScoreCorrect = true)
                setNewSellScore(id, newScore, newTable)
            }
            "" -> Unit
            else -> {
                val newTable = buildNewTable(id = id, newScore = newScore, isScoreCorrect = false)
                newTable.leaveCellFocus(id)
                state.update { CompetitionTableState(scoreSellItems = newTable, isScoreCorrect = false) }
            }
        }

    }

    private fun buildNewTable(id: Int, newScore: String, isScoreCorrect: Boolean): MutableList<TableListItem> {
        val newTable = state.value.scoreSellItems.toMutableList()
        newTable[id] = ScoreSellItem(
            id = id,
            score = newScore,
            isScoreCorrect = isScoreCorrect
        )
        return newTable
    }

    private fun setNewSellScore(id: Int, newScore: String, newTable: MutableList<TableListItem>) {
        val sameScoredId = getSameScoredId(id, newTable.size)
        newTable[sameScoredId] = ScoreSellItem(id = sameScoredId, score = newScore)

        newTable.changeCellFocus(id)

        val firstLineIndex = id % TABLE_LENGTH
        val secondLineIndex = sameScoredId % TABLE_LENGTH

        state.update {
            val totalScoreItems = it.totalScoreItems
                .toMutableList()
                .apply {
                    this[firstLineIndex] = TotalScoreItem(newTable.countLineTotalScore(firstLineIndex))
                    this[secondLineIndex] = TotalScoreItem(newTable.countLineTotalScore(secondLineIndex))
                }

            val winnerItems = totalScoreItems
                .mapIndexed { initialIndex, totalScoreItem -> initialIndex to totalScoreItem.score }
                .sortedBy { (_, score) -> score }
                .mapIndexed { place, (initialIndex, _) -> initialIndex to place }
                .sortedBy { (initialIndex, _) -> initialIndex }
                .map { (_, place) -> WinnerItem((place + 1).toString()) }

            CompetitionTableState(
                scoreSellItems = newTable,
                isScoreCorrect = true,
                totalScoreItems = totalScoreItems,
                winnerItems = winnerItems
            )
        }
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

    private fun MutableList<TableListItem>.changeCellFocus(id: Int) {
        val nextId = id + 1
        if (this[id] is ScoreSellItem) {
            this[id] = (this[id] as ScoreSellItem).copy(isFocused = false)
        }
        if (nextId == TABLE_LENGTH * TABLE_LENGTH) return

        val nextItem = this[nextId]

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

    private fun MutableList<TableListItem>.countLineTotalScore(lineIndex: Int): String {
        var total = 0

        if (isLineFilled(lineIndex, this)) {
            for (item in this) {
                if (item is ScoreSellItem
                    && item.id % TABLE_LENGTH == lineIndex
                ) {
                    total += item.score.toInt()
                }
            }
        }

        return if (total == 0) ""
        else total.toString()
    }

    private fun isLineFilled(lineIndex: Int, table: MutableList<TableListItem>): Boolean {
        var filledCellsCounter = 0

        for (item in table) {
            if (item is ScoreSellItem
                && item.id % TABLE_LENGTH == lineIndex
                && item.score.isNotEmpty()
            ) {
                filledCellsCounter++
            }
        }
        return filledCellsCounter == TABLE_LENGTH - 1
    }

    private fun setState() {
        state.update {
            CompetitionTableState(
                scoreSellItems = buildTableCells(),
                totalScoreItems = buildTotalScoreCells(),
                winnerItems = buildWinnersCells()
            )
        }
    }

    private fun buildTableCells(): List<TableListItem> {
        val list = mutableListOf<TableListItem>()

        for (i in 0 until TABLE_LENGTH * TABLE_LENGTH) {
            if (i % (TABLE_LENGTH + 1) == 0) {
                list.add(ScoreMiddleItem)
            } else {
                list.add(
                    ScoreSellItem(id = i, score = "")
                )
            }
        }
        return list
    }

    private fun buildTotalScoreCells(): List<TotalScoreItem> {
        val list = mutableListOf<TotalScoreItem>()

        for (i in 0 until TABLE_LENGTH) {
            list.add(
                TotalScoreItem(score = "")
            )
        }
        return list
    }

    private fun buildWinnersCells(): MutableList<WinnerItem> {
        val list = mutableListOf<WinnerItem>()

        for (i in 0 until TABLE_LENGTH) {
            list.add(
                WinnerItem(place = "")
            )
        }
        return list
    }

    companion object {
        const val TABLE_LENGTH = 7
    }
}