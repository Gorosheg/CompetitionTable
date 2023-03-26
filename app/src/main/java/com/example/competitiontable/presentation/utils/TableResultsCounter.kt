package com.example.competitiontable.presentation.utils

import com.example.competitiontable.presentation.CompetitionTableViewModel
import com.example.competitiontable.presentation.builder.buildEmptyWinnerItems
import com.example.competitiontable.presentation.model.ScoreCellItem
import com.example.competitiontable.presentation.model.Cell
import com.example.competitiontable.presentation.model.TotalScoreItem
import com.example.competitiontable.presentation.model.WinnerItem

fun List<TotalScoreItem>.countWinnerPlaces(): List<WinnerItem> {
    for (item in this) {
        if (item.score.isEmpty()) {
            return buildEmptyWinnerItems()
        }
    }

    return asSequence()
        .mapIndexed { initialIndex, totalScoreItem -> initialIndex to totalScoreItem.score }
        .sortedBy { (_, score) -> score }
        .mapIndexed { place, (initialIndex, _) -> initialIndex to place }
        .sortedBy { (initialIndex, _) -> initialIndex }
        .map { (_, place) -> place + 1 }
        .map { place -> WinnerItem(place.toString()) }
        .toList()
}

fun List<Cell>.countLineTotalScore(lineIndex: Int): String {
    var total = 0

    if (isLineFilled(lineIndex)) {
        for (item in this) {
            if (item is ScoreCellItem
                && item.id % CompetitionTableViewModel.TABLE_LENGTH == lineIndex
            ) {
                total += item.score.toInt()
            }
        }
    }

    return if (total == 0) "" else total.toString()
}

private fun List<Cell>.isLineFilled(lineIndex: Int): Boolean {
    var filledCellsCounter = 0

    for (item in this) {
        if (item is ScoreCellItem
            && item.id % CompetitionTableViewModel.TABLE_LENGTH == lineIndex
            && item.score.isNotEmpty()
        ) {
            filledCellsCounter++
        }
    }

    return filledCellsCounter == CompetitionTableViewModel.TABLE_LENGTH - 1
}
