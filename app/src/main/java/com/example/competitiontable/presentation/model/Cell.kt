package com.example.competitiontable.presentation.model

import com.example.competitiontable.ui.recycler.base.ListItem

interface Cell : ListItem

data class ScoreCellItem(
    val id: Int,
    val score: String,
    val isScoreCorrect: Boolean = true,
    val isFocused: Boolean = false
) : Cell

object ScoreMiddleItem : Cell