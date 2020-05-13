package com.testtask2.library_base.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testtask2.library_base.presentation.extention.toLiveData
import kotlin.properties.Delegates

public abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseAction>(initialState: ViewState) :
    ViewModel() {

    private val stateMutableLiveData = MutableLiveData<ViewState>()
    val stateLiveData = stateMutableLiveData.toLiveData()

    // Delegate will handle state event deduplication
    // (multiple states of the same type holding the same data will not be dispatched multiple times to LiveData stream)
    protected var state by Delegates.observable(initialState) { _, old, new ->
        stateMutableLiveData.value = new
    }

    fun sendAction(viewAction: ViewAction) {
        state = onReduceState(viewAction)
    }

    fun loadData(isFresh: Boolean = false, location: Location? = null) {
        onLoadData(isFresh)
    }

    protected open fun onLoadData(isFresh: Boolean = false) {}

    protected abstract fun onReduceState(viewAction: ViewAction): ViewState
}
