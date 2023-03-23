package com.example.competitiontable.presentation.model

import com.example.competitiontable.presentation.recycler.base.ListItem

class ScoreSellItem(
    val id: Int,
    val score: String,
    val isScoreCorrect: Boolean = true,
) : ListItem