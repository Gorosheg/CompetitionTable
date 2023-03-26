package com.example.competitiontable.presentation.recycler

import androidx.core.widget.addTextChangedListener
import com.example.competitiontable.R
import com.example.competitiontable.databinding.TableCellBinding
import com.example.competitiontable.presentation.model.ScoreSellItem
import com.example.competitiontable.presentation.recycler.base.adapterDelegate

fun tableDelegate(onScoreChanged: (id: Int, text: String) -> Unit) =
    adapterDelegate<ScoreSellItem, TableCellBinding>(TableCellBinding::inflate) {

        val listener = tableSell.addTextChangedListener { text ->
            onScoreChanged(item.id, text.toString())
        }

        bind {
            tableSell.removeTextChangedListener(listener)
            tableSell.setText(item.score)
            tableSell.addTextChangedListener(listener)

            if (!item.isScoreCorrect) {
                tableSell.setTextColor(context.getColor(R.color.red))
            } else {
                tableSell.setTextColor(context.getColor(R.color.black))
            }

            if (item.isFocused) {
                tableSell.requestFocus()
            }
        }
    }