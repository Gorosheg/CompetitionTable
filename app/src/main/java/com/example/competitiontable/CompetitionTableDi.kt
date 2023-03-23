package com.example.competitiontable

import com.example.competitiontable.presentation.CompetitionTableViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val competitionTableModule = module {

    viewModel {
        CompetitionTableViewModel()
    }
}