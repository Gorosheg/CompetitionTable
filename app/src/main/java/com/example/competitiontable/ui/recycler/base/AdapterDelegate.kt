package com.example.competitiontable.ui.recycler.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

inline fun <reified Item : ListItem, reified Binding : ViewBinding> adapterDelegate(
    noinline viewBinding: (layoutInflater: LayoutInflater, root: ViewGroup, attachToParent: Boolean) -> Binding,
    noinline block: context(Binding) AdapterDelegateViewBindingViewHolder<Item, Binding>.() -> Unit = { },
): AdapterDelegate<List<ListItem>> {
    return adapterDelegateViewBinding<Item, ListItem, Binding>(
        viewBinding = { layoutInflater, parent -> viewBinding.invoke(layoutInflater, parent, false) },
        block = { block.invoke(binding, this) },
    )
}