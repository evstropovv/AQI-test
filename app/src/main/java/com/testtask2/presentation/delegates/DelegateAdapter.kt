package com.testtask2.presentation.delegates

import androidx.fragment.app.Fragment
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.testtask2.domain.model.Station
import com.testtask2.domain.model.delegate.DisplayableItem

class DelegateAdapter constructor() : ListDelegationAdapter<List<DisplayableItem>>() {

    constructor(fragment: Fragment, listener: (Station) -> Unit) : this() {
        delegatesManager.addDelegate(StationDelegate(fragment, listener))
    }
}