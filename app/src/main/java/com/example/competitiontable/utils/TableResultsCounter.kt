package com.example.competitiontable.utils

import com.example.competitiontable.presentation.CompetitionTableViewModel
import com.example.competitiontable.presentation.model.ScoreSellItem
import com.example.competitiontable.presentation.model.TableListItem
import com.example.competitiontable.presentation.model.TotalScoreItem
import com.example.competitiontable.presentation.model.WinnerItem

fun List<TableListItem>.countLineTotalScore(lineIndex: Int): String {
    var total = 0

    if (isLineFilled(lineIndex)) {
        for (item in this) {
            if (item is ScoreSellItem
                && item.id % CompetitionTableViewModel.TABLE_LENGTH == lineIndex
            ) {
                total += item.score.toInt()
            }
        }
    }

    return if (total == 0) ""
    else total.toString()
}

private fun List<TableListItem>.isLineFilled(lineIndex: Int): Boolean {
    var filledCellsCounter = 0

    for (item in this) {
        if (item is ScoreSellItem
            && item.id % CompetitionTableViewModel.TABLE_LENGTH == lineIndex
            && item.score.isNotEmpty()
        ) {
            filledCellsCounter++
        }
    }
    return filledCellsCounter == CompetitionTableViewModel.TABLE_LENGTH - 1
}

fun List<TotalScoreItem>.countWinnerPlaces(): List<WinnerItem> {
    for (i in this) {
        if (i.score == "") return buildEmptyWinnersCells()
    }

    return mapIndexed { initialIndex, totalScoreItem -> initialIndex to totalScoreItem.score }
        .sortedBy { (_, score) -> score }
        .mapIndexed { place, (initialIndex, _) -> initialIndex to place }
        .sortedBy { (initialIndex, _) -> initialIndex }
        .map { (_, place) -> WinnerItem((place + 1).toString()) }
}
