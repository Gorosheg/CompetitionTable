package com.example.competitiontable.presentation.utils

import com.example.competitiontable.presentation.CompetitionTableViewModel.Companion.TABLE_LENGTH
import com.example.competitiontable.presentation.model.ScoreMiddleItem
import com.example.competitiontable.presentation.model.ScoreCellItem
import com.example.competitiontable.presentation.model.Cell

fun MutableList<Cell>.changeCellFocus(newScore: String, id: Int) {
    if (newScore.isBlank()) leaveCellFocus(id)
    else changeCellFocus(id)
}

fun MutableList<Cell>.leaveCellFocus(id: Int) {
    forEach {
        if (it is ScoreCellItem) {
            this[it.id] = (this[it.id] as ScoreCellItem).copy(isFocused = false)
        }
    }

    if (this[id] is ScoreCellItem) {
        this[id] = (this[id] as ScoreCellItem).copy(isFocused = true)
    }
}

private fun MutableList<Cell>.changeCellFocus(id: Int) {
    val nextId = id + 1

    if (nextId == TABLE_LENGTH * TABLE_LENGTH) return

    val nextItem = this[nextId]

    forEach {
        if (it is ScoreCellItem) {
            this[it.id] = (this[it.id] as ScoreCellItem).copy(isFocused = false)
        }
    }

    if (nextItem is ScoreMiddleItem || nextItem is ScoreCellItem && nextItem.score.isNotEmpty()) {
        changeCellFocus(nextId)
    } else {
        this[nextId] = (nextItem as ScoreCellItem).copy(isFocused = true)
    }
}