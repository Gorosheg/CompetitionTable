package com.example.competitiontable.presentation.recycler

import androidx.core.widget.addTextChangedListener
import com.example.competitiontable.R
import com.example.competitiontable.databinding.TableCellBinding
import com.example.competitiontable.presentation.model.ScoreSellItem
import com.example.competitiontable.presentation.recycler.base.adapterDelegate

fun tableDelegate(onScoreChanged: (id: Int, text: String) -> Unit) =
    adapterDelegate<ScoreSellItem, TableCellBinding>(TableCellBinding::inflate) {

        tableSell.addTextChangedListener { text ->
            onScoreChanged(item.id, text.toString())
        }

        bind {
            tableSell.setText(item.score)

            if (!item.isScoreCorrect) {
                tableSell.setTextColor(context.getColor(R.color.red))
            } else {
                tableSell.setTextColor(context.getColor(R.color.black))
            }
        }
    }