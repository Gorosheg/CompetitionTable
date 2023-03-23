package com.example.competitiontable.presentation.recycler

import com.example.competitiontable.R
import com.example.competitiontable.databinding.ScoreCellBinding
import com.example.competitiontable.presentation.model.ScoreMiddleItem
import com.example.competitiontable.presentation.recycler.base.adapterDelegate

fun scoreTableMiddleCellDelegate() = adapterDelegate<ScoreMiddleItem, ScoreCellBinding>(ScoreCellBinding::inflate) {

    bind {
        scoreSell.setBackgroundColor(context.getColor(R.color.black))
        scoreSell.isEnabled = false
    }
}