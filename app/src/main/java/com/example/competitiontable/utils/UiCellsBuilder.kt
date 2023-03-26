package com.example.competitiontable.utils

import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.setPadding
import com.example.competitiontable.R
import com.example.competitiontable.presentation.CompetitionTableViewModel

fun LinearLayout.buildCounter() {
    for (i in 0 until CompetitionTableViewModel.TABLE_LENGTH) {
        buildCountableCell((i + 1).toString())
    }
}

fun LinearLayout.buildCountableCell(text: String, width: Int = 105) {
    val counter = TextView(context)
    counter.background = AppCompatResources.getDrawable(context, R.drawable.background_table_stroke)
    counter.layoutParams = LinearLayout.LayoutParams(width, 105)
    counter.gravity = Gravity.CENTER
    counter.text = text
    addView(counter)
}

fun LinearLayout.buildParticipants() {
    for (i in 0 until CompetitionTableViewModel.TABLE_LENGTH) {
        val number = i + 1
        val participant = TextView(context)
        participant.background = AppCompatResources.getDrawable(context, R.drawable.background_table_stroke)
        participant.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 105)
        participant.setPadding(12)
        participant.gravity = Gravity.CENTER

        participant.text = java.lang.String.format(
            context.getString(R.string.participant_name),
            number
        )

        addView(participant)
    }
}