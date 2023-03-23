package com.example.competitiontable.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.competitiontable.presentation.model.CompetitionTableState
import com.example.competitiontable.presentation.model.ScoreSellItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CompetitionTableViewModel : ViewModel() {

    val state = MutableStateFlow(CompetitionTableState())

    init {
        setState()
    }

    private fun setState() {
        viewModelScope.launch {
            state.update {
                CompetitionTableState(
                    scoreSellItems = getInitScoreSells()
                )
            }
        }
    }

    private fun getInitScoreSells(): List<ScoreSellItem> {
        val list = mutableListOf<ScoreSellItem>()
        for (i in 0 until TABLE_LENGTH * TABLE_LENGTH) {
            list.add(ScoreSellItem(id = i, score = ""))
        }
        return list
    }

    companion object {
        private const val TABLE_LENGTH = 7
    }
}