package com.example.competitiontable.presentation.builder

import com.example.competitiontable.presentation.CompetitionTableViewModel
import com.example.competitiontable.presentation.model.*

fun buildEmptyCells(): List<Cell> {
    val list = mutableListOf<Cell>()

    for (i in 0 until CompetitionTableViewModel.TABLE_LENGTH * CompetitionTableViewModel.TABLE_LENGTH) {
        if (i % (CompetitionTableViewModel.TABLE_LENGTH + 1) == 0) {
            list.add(ScoreMiddleItem)
        } else {
            list.add(ScoreCellItem(id = i, score = ""))
        }
    }

    return list
}

fun buildEmptyTotalScoreItems(): List<TotalScoreItem> {
    return mutableListOf<TotalScoreItem>().also { list ->
        for (i in 0 until CompetitionTableViewModel.TABLE_LENGTH) {
            list.add(TotalScoreItem())
        }
    }
}

fun buildEmptyWinnerItems(): List<WinnerItem> {
    return mutableListOf<WinnerItem>().also { list ->
        for (i in 0 until CompetitionTableViewModel.TABLE_LENGTH) {
            list.add(WinnerItem())
        }
    }
}

