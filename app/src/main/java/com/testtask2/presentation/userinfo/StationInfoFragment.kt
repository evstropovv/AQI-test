package com.testtask2.presentation.userinfo

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.testtask2.App
import com.testtask2.R
import com.testtask2.di.ViewModelFactory
import com.testtask2.library_base.presentation.extention.visible
import com.testtask2.library_base.presentation.fragment.BaseContainerFragment
import kotlinx.android.synthetic.main.fragment.progressBar
import kotlinx.android.synthetic.main.fragment.swiperefresh
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
class StationInfoFragment : BaseContainerFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: StationInfoViewModel

    override val layoutResourceId: Int = R.layout.fragment_info

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).getComponent()?.inject(this)
        viewModel = ViewModelProvider(this, factory).get(StationInfoViewModel::class.java)
        arguments?.getLong("id")?.let {
            viewModel.setId(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.stateLiveData.observe(viewLifecycleOwner,
            Observer<StationInfoViewModel.ViewState> {
                progressBar.visible(it.isLoading)
                textView.text = it.text
            })

        swiperefresh.setOnRefreshListener {
            viewModel.loadData(true)
            swiperefresh.isRefreshing = false
        }
    }

}