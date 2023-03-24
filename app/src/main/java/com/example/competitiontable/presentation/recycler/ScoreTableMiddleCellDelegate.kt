package com.example.competitiontable.presentation.recycler

import com.example.competitiontable.R
import com.example.competitiontable.databinding.TableCellBinding
import com.example.competitiontable.presentation.model.ScoreMiddleItem
import com.example.competitiontable.presentation.recycler.base.adapterDelegate

fun tableMiddleCellDelegate() = adapterDelegate<ScoreMiddleItem, TableCellBinding>(TableCellBinding::inflate) {

    bind {
        tableSell.setBackgroundColor(context.getColor(R.color.black))
        tableSell.isEnabled = false
    }
}