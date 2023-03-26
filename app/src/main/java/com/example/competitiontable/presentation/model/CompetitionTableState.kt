package com.example.competitiontable.presentation.model

data class CompetitionTableState(
    val scoreSellItems: List<TableListItem> = emptyList(),
    val isScoreCorrect: Boolean = true,
    val totalScoreItems: List<TotalScoreItem> = listOf(),
    val winnerItems: List<WinnerItem> = listOf(),
)