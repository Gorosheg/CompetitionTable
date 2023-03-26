package com.example.competitiontable.presentation.builder

import com.example.competitiontable.presentation.CompetitionTableViewModel.Companion.TABLE_LENGTH
import com.example.competitiontable.presentation.model.CompetitionTableState
import com.example.competitiontable.presentation.model.ScoreCellItem
import com.example.competitiontable.presentation.model.Cell
import com.example.competitiontable.presentation.model.TotalScoreItem
import com.example.competitiontable.presentation.utils.changeCellFocus
import com.example.competitiontable.presentation.utils.countLineTotalScore
import com.example.competitiontable.presentation.utils.countWinnerPlaces

fun buildCorrectState(
    scoreSellItems: List<Cell>,
    totalScoreItems: List<TotalScoreItem>,
    id: Int,
    score: String
): CompetitionTableState {
    val newTable = buildTable(
        scoreSellItems = scoreSellItems,
        id = id,
        newScore = score,
        isScoreCorrect = true
    )

    val sameScoredId = getSameScoredId(id, newTable.size)

    newTable[sameScoredId] = ScoreCellItem(id = sameScoredId, score = score)

    newTable.changeCellFocus(newScore = score, id = id)

    return buildState(
        totalScoreItems = totalScoreItems,
        firstLineIndex = id % TABLE_LENGTH,
        newTable = newTable,
        secondLineIndex = sameScoredId % TABLE_LENGTH
    )
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

private fun buildState(
    totalScoreItems: List<TotalScoreItem>,
    firstLineIndex: Int,
    newTable: List<Cell>,
    secondLineIndex: Int,
): CompetitionTableState {
    val newTotalScoreItems = totalScoreItems
        .toMutableList()
        .apply {
            this[firstLineIndex] = TotalScoreItem(newTable.countLineTotalScore(firstLineIndex))
            this[secondLineIndex] = TotalScoreItem(newTable.countLineTotalScore(secondLineIndex))
        }

    val winnerItems = newTotalScoreItems.countWinnerPlaces()

    return CompetitionTableState(
        scoreCellItems = newTable,
        isScoreCorrect = true,
        totalScoreItems = newTotalScoreItems,
        winnerItems = winnerItems
    )
}