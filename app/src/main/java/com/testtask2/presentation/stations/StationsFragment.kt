package com.testtask2.presentation.stations

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.testtask2.App
import com.testtask2.R
import com.testtask2.di.ViewModelFactory
import com.testtask2.library_base.presentation.extention.visible
import com.testtask2.library_base.presentation.fragment.BaseContainerFragment
import com.testtask2.presentation.delegates.DelegateAdapter
import kotlinx.android.synthetic.main.fragment.*
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener


@InternalCoroutinesApi
class StationsFragment : BaseContainerFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: StationsViewModel

    override val layoutResourceId: Int = R.layout.fragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).getComponent()?.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val navController = NavHostFragment.findNavController(this)
        viewModel =
            ViewModelProvider(navController.getViewModelStoreOwner(R.id.navigation), factory).get(
                StationsViewModel::class.java
            )

        viewModel.stateLiveData.observe(viewLifecycleOwner,
            Observer<StationsViewModel.ViewState> {
                progressBar.visible(it.isLoading)
                val adapter = DelegateAdapter(this) {
                    val bundle = Bundle()
                    bundle.putLong("id", it.id)
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.action_stationsFragment_to_userInfoFragment, bundle)
                }
                adapter.items = it.displayableItems
                rv.layoutManager = LinearLayoutManager(requireContext())
                rv.adapter = adapter
            })

        swiperefresh.setOnRefreshListener {
            checkPermissionAndLoadData()
            swiperefresh.isRefreshing = false
        }
        checkPermissionAndLoadData()
    }

    private fun checkPermissionAndLoadData() {
        val snackbarMultiplePermissionsListener =
            SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                .with(view, "geolocation permission is required to load stations info")
                .withOpenSettingsButton("Settings")
                .build()

        Dexter.withContext(requireContext())
            .withPermissions(permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION)
            .withListener(object :
                CompositeMultiplePermissionsListener(snackbarMultiplePermissionsListener,
                    object : MultiplePermissionsListener {
                        override fun onPermissionRationaleShouldBeShown(
                            p0: MutableList<PermissionRequest>?,
                            p1: PermissionToken?
                        ) {
                        }

                        @SuppressLint("MissingPermission")
                        override fun onPermissionsChecked(p: MultiplePermissionsReport?) {
                            if (p?.areAllPermissionsGranted()!!) {
                                SingleShotLocationProvider.requestSingleUpdate(requireContext()) { location ->
                                    run {
                                        viewModel.updateLocation(location)
                                    }
                                }
                            }
                        }
                    }) {

            }).check()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filters) {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_stationsFragment_to_filterDialog)
        }
        return super.onOptionsItemSelected(item)
    }
}