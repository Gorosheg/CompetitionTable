package com.example.competitiontable.presentation.builder

import com.example.competitiontable.presentation.model.ScoreCellItem
import com.example.competitiontable.presentation.model.Cell

fun buildTable(
    scoreSellItems: List<Cell>,
    id: Int,
    newScore: String,
    isScoreCorrect: Boolean,
): MutableList<Cell> {
    val newTable = scoreSellItems.toMutableList()

    newTable[id] = ScoreCellItem(
        id = id,
        score = newScore,
        isScoreCorrect = isScoreCorrect
    )

    return newTable
}