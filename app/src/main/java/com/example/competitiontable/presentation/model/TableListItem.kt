package com.example.competitiontable.presentation.model

import com.example.competitiontable.presentation.recycler.base.ListItem

interface TableListItem : ListItem

data class ScoreSellItem(
    val id: Int,
    val score: String,
    val isScoreCorrect: Boolean = true,
    val isFocused: Boolean = false
) : TableListItem

object ScoreMiddleItem : TableListItem