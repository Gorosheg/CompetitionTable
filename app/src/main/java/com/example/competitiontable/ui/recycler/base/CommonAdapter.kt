package com.example.competitiontable.ui.recycler.base

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

open class CommonAdapter private constructor(
    private val delegatesManager: AdapterDelegatesManager<List<ListItem>>,
) : ListDelegationAdapter<List<ListItem>>(delegatesManager) {

    constructor(vararg delegates: AdapterDelegate<List<ListItem>>) : this(
        delegatesManager = AdapterDelegatesManager(),
    ) {
        delegates.forEach(delegatesManager::addDelegate)
    }
}