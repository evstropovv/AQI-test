package com.testtask2.presentation.userinfo

import androidx.lifecycle.viewModelScope
import com.testtask2.domain.usecase.GetStationInfoUseCase
import com.testtask2.library_base.presentation.viewmodel.BaseAction
import com.testtask2.library_base.presentation.viewmodel.BaseViewModel
import com.testtask2.library_base.presentation.viewmodel.BaseViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class StationInfoViewModel @Inject constructor(
    private val stationInfoUseCase: GetStationInfoUseCase
) : BaseViewModel<StationInfoViewModel.ViewState, StationInfoViewModel.Action>(ViewState()) {

    var selectedPost: Long? = null
    private var job: Job? = null

    override fun onLoadData(isFresh: Boolean) {
        selectedPost?.let {
            loadInfo(it)
        }
    }

    fun setId(id: Long) {
        this.selectedPost = id
        loadInfo(id)
    }

    fun loadInfo(id: Long) {

        job = viewModelScope.launch {
            stationInfoUseCase.execute(id, this).collect {
                if (it != null) {
                    sendAction(Action.StationInfoSuccess(it))
                } else {
                    sendAction(Action.StationInfoLoadFailure)
                }
            }
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        is Action.StationInfoSuccess -> state.copy(
            isLoading = false,
            isError = false,
            text = viewAction.text
        )
        is Action.StationInfoLoadFailure -> state.copy(
            isLoading = false,
            isError = true,
            text = ""
        )
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val text: String = ""
    ) : BaseViewState

    sealed class Action : BaseAction {
        class StationInfoSuccess(val text: String) : Action()
        object StationInfoLoadFailure : Action()
    }

}