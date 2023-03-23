package com.example.competitiontable.presentation.recycler

import androidx.core.widget.addTextChangedListener
import com.example.competitiontable.R
import com.example.competitiontable.databinding.ScoreCellBinding
import com.example.competitiontable.presentation.model.ScoreSellItem
import com.example.competitiontable.presentation.recycler.base.adapterDelegate

fun scoreTableDelegate(onScoreChanged: (id: Int, text: String) -> Unit) =
    adapterDelegate<ScoreSellItem, ScoreCellBinding>(ScoreCellBinding::inflate) {

        scoreSell.addTextChangedListener { text ->
            onScoreChanged(item.id, text.toString())
        }

        bind {
            scoreSell.setText(item.score)

            if (!item.isScoreCorrect) {
                scoreSell.setTextColor(context.getColor(R.color.red))
            } else {
                scoreSell.setTextColor(context.getColor(R.color.black))
            }
        }
    }