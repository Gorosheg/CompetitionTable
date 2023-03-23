package com.example.competitiontable.presentation.recycler

import com.example.competitiontable.databinding.ScoreCellBinding
import com.example.competitiontable.presentation.recycler.base.adapterDelegate
import com.example.competitiontable.presentation.model.ScoreSellItem

fun scoreTableDelegate() =
    adapterDelegate<ScoreSellItem, ScoreCellBinding>(ScoreCellBinding::inflate) {
    }