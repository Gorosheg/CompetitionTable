package com.example.competitiontable.utils

import com.example.competitiontable.presentation.CompetitionTableViewModel
import com.example.competitiontable.presentation.model.*

fun buildNewTable(
    scoreSellItems: List<TableListItem>,
    id: Int,
    newScore: String,
    isScoreCorrect: Boolean,
): MutableList<TableListItem> {
    val newTable = scoreSellItems.toMutableList()
    newTable[id] = ScoreSellItem(
        id = id,
        score = newScore,
        isScoreCorrect = isScoreCorrect
    )
    return newTable
}


fun buildEmptyTableCells(): List<TableListItem> {
    val list = mutableListOf<TableListItem>()

    for (i in 0 until CompetitionTableViewModel.TABLE_LENGTH * CompetitionTableViewModel.TABLE_LENGTH) {
        if (i % (CompetitionTableViewModel.TABLE_LENGTH + 1) == 0) {
            list.add(ScoreMiddleItem)
        } else {
            list.add(
                ScoreSellItem(id = i, score = "")
            )
        }
    }
    return list
}

fun buildEmptyTotalScoreCells(): List<TotalScoreItem> {
    val list = mutableListOf<TotalScoreItem>()

    for (i in 0 until CompetitionTableViewModel.TABLE_LENGTH) {
        list.add(
            TotalScoreItem(score = "")
        )
    }
    return list
}

fun buildEmptyWinnersCells(): List<WinnerItem> {
    val list = mutableListOf<WinnerItem>()

    for (i in 0 until CompetitionTableViewModel.TABLE_LENGTH) {
        list.add(
            WinnerItem(place = "")
        )
    }
    return list
}

