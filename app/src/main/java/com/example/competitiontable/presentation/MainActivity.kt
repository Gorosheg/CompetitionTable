package com.example.competitiontable.presentation

import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.competitiontable.R
import com.example.competitiontable.databinding.ActivityMainBinding
import com.example.competitiontable.presentation.CompetitionTableViewModel.Companion.TABLE_LENGTH
import com.example.competitiontable.presentation.model.CompetitionTableState
import com.example.competitiontable.presentation.recycler.base.CommonAdapter
import com.example.competitiontable.presentation.recycler.scoreTableDelegate
import com.example.competitiontable.presentation.recycler.scoreTableMiddleCellDelegate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.String.format

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: CompetitionTableViewModel by viewModel()

    private val scoreTableAdapter = CommonAdapter(
        scoreTableMiddleCellDelegate(),
        scoreTableDelegate { id, text ->
            viewModel.onSellTextChanged(id, text)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(binding) {

            scoreRecycler.layoutManager = GridLayoutManager(this@MainActivity, 7)
            scoreRecycler.adapter = scoreTableAdapter

            viewModel.state.onEach { state -> renderState(state) }.launchIn(lifecycleScope)

            buildCounter(verticalCounterLayout)
            buildCounter(horizontalCounterLayout)

            buildParticipants(participantsLayout)
        }
    }

    private fun buildParticipants(participantsLayout: LinearLayout) {
        for (i in 0 until TABLE_LENGTH) {
            val number = i + 1
            val participant = TextView(this)
            participant.background = AppCompatResources.getDrawable(this, R.drawable.background_table_stroke)
            participant.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 105)
            participant.setPadding(12)
            participant.gravity = Gravity.CENTER

            participant.text = format(
                getString(R.string.participant_name),
                number
            )

            participantsLayout.addView(participant)
        }
    }

    private fun buildCounter(counterLayout: LinearLayout) {
        for (i in 0 until TABLE_LENGTH) {
            val number = i + 1
            val counter = TextView(this)
            counter.background = AppCompatResources.getDrawable(this, R.drawable.background_table_stroke)
            counter.layoutParams = LinearLayout.LayoutParams(105, 105)
            counter.gravity = Gravity.CENTER
            counter.text = number.toString()
            counterLayout.addView(counter)
        }
    }

    private fun renderState(state: CompetitionTableState) = with(binding) {
        scoreTableAdapter.items = state.scoreSellItems

        if (!scoreRecycler.isComputingLayout) {
            scoreTableAdapter.notifyDataSetChanged()
        }

        if (!state.isScoreCorrect) {
            Toast.makeText(this@MainActivity, getString(R.string.toast_messaage), Toast.LENGTH_SHORT).show()
        }
    }
}