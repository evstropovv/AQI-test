package com.testtask2.presentation.stations

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.testtask2.App
import com.testtask2.R
import kotlinx.android.synthetic.main.dialog_params.*
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject
import androidx.navigation.fragment.NavHostFragment

@InternalCoroutinesApi
class FilterDialog : BottomSheetDialogFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: StationsViewModel

    override fun onAttach(context: Context) {
        (activity?.application as App).getComponent()?.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val navController = NavHostFragment.findNavController(this@FilterDialog)
        viewModel =
            ViewModelProvider(navController.getViewModelStoreOwner(R.id.navigation), factory).get(
                StationsViewModel::class.java
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_params, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        addRadioButtons(rgAqi, rgRadius)

        rgRadius.setOnCheckedChangeListener { group, checkedId ->
            viewModel.loadStations(checkedId, rgAqi.checkedRadioButtonId)
            this.dismiss()
        }

        rgAqi.setOnCheckedChangeListener { group, checkedId ->
            viewModel.loadStations(rgRadius.checkedRadioButtonId, checkedId)
            this.dismiss()
        }
    }

    private fun addRadioButtons(rgAqi: RadioGroup, rgRadius: RadioGroup) {
        viewModel.aqiList.forEach { item ->
            val rb = RadioButton(requireContext()).apply {
                id = item
                text = "$item"
            }
            rb.isChecked = (viewModel.filters.minAqi == rb.id)
            rgAqi.addView(rb)
        }
        viewModel.radiusList.forEach { item ->
            val rb = RadioButton(requireContext()).apply {
                id = item
                text = "$item km"
            }
            rb.isChecked = (viewModel.filters.radius == rb.id)
            rgRadius.addView(rb)
        }
    }


}