package com.example.competitiontable.ui.recycler

import androidx.core.widget.addTextChangedListener
import com.example.competitiontable.R
import com.example.competitiontable.databinding.TableCellBinding
import com.example.competitiontable.presentation.model.ScoreCellItem
import com.example.competitiontable.ui.recycler.base.adapterDelegate

fun tableDelegate(onScoreChanged: (id: Int, text: String) -> Unit) =
    adapterDelegate<ScoreCellItem, TableCellBinding>(TableCellBinding::inflate) {

        val listener = tableSell.addTextChangedListener { text ->
            onScoreChanged(item.id, text.toString())
        }

        bind {
            tableSell.removeTextChangedListener(listener)
            tableSell.setText(item.score)
            tableSell.addTextChangedListener(listener)

            val color =
                if (!item.isScoreCorrect) R.color.red
                else R.color.black

            tableSell.setTextColor(context.getColor(color))

            if (item.isFocused) {
                tableSell.requestFocus()
            }
        }
    }