package com.testtask2.presentation.stations

import android.location.Location
import androidx.lifecycle.viewModelScope
import com.testtask2.domain.model.Filters
import com.testtask2.domain.model.Station
import com.testtask2.domain.model.delegate.DisplayableItem
import com.testtask2.domain.usecase.GetStationListUseCase
import com.testtask2.library_base.presentation.viewmodel.BaseAction
import com.testtask2.library_base.presentation.viewmodel.BaseViewModel
import com.testtask2.library_base.presentation.viewmodel.BaseViewState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
class StationsViewModel @Inject constructor(
    private val getStationListUseCase: GetStationListUseCase
) : BaseViewModel<StationsViewModel.ViewState, StationsViewModel.Action>(ViewState()) {

    private var job: Job? = null

    val aqiList = arrayListOf<Int>(0, 5, 10, 20, 50, 100, 200)
    val radiusList = arrayListOf<Int>(5, 10, 20, 50, 100)

    var filters = Filters(radius = 20, minAqi = 0)
    var location: Location? = null

    override fun onLoadData(isFresh: Boolean) {
        loadStations(filters.radius, filters.minAqi)
    }

    fun updateLocation(location: Location?){
        this.location = location
        loadStations(filters.radius, filters.minAqi)
    }

    fun loadStations(radius: Int, minAqi: Int) {
        filters = filters.copy(radius = radius, minAqi = minAqi)
        if (location == null)
            return
        job?.cancel()
        job = viewModelScope.launch {
            getStationListUseCase.execute(location!!, radius, minAqi, this).collect {
                if (it != null) {
                    sendAction(Action.StationsLoadSuccess(it))
                } else {
                    sendAction(Action.StationsLoadFailure)
                }
            }
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        is Action.StationsLoadSuccess -> state.copy(
            isLoading = false,
            isError = false,
            displayableItems = viewAction.postsList
        )
        is Action.StationsLoadFailure -> state.copy(
            isLoading = false,
            isError = true,
            displayableItems = ArrayList()
        )
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val displayableItems: List<DisplayableItem> = ArrayList()
    ) : BaseViewState

    sealed class Action : BaseAction {
        class StationsLoadSuccess(val postsList: List<Station>) : Action()
        object StationsLoadFailure : Action()
    }

}