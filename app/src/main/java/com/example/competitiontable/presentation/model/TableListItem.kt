package com.example.competitiontable.presentation.model

import com.example.competitiontable.presentation.recycler.base.ListItem

interface TableListItem : ListItem

class ScoreSellItem(
    val id: Int,
    val score: String,
    val isScoreCorrect: Boolean = true,
) : TableListItem

object ScoreMiddleItem : TableListItem