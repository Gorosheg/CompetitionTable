package com.example.competitiontable.presentation.model

data class CompetitionTableState(
    val scoreCellItems: List<Cell> = emptyList(),
    val isScoreCorrect: Boolean = true,
    val totalScoreItems: List<TotalScoreItem> = listOf(),
    val winnerItems: List<WinnerItem> = listOf(),
)