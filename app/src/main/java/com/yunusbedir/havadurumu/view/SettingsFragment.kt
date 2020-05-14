package com.yunusbedir.havadurumu.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yunusbedir.havadurumu.Model.Region
import com.yunusbedir.havadurumu.Model.User

import com.yunusbedir.havadurumu.R
import com.yunusbedir.havadurumu.ViewModel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_current.*
import kotlinx.android.synthetic.main.layout_error.*

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {
    private val TAG = "SettingsFragment"
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadSettings()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        viewModel.user.observe(viewLifecycleOwner, renderUser)
        viewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        viewModel.isEmpty.observe(viewLifecycleOwner, emptyObserver)
    }

    private val renderUser = Observer<User> { user ->
        Log.v(TAG, "data updated $user")
        incLayoutError.visibility = View.GONE
        incLayoutEmpty.visibility = View.GONE

    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.i(TAG, "isViewLoading $it")
        swipeRefreshLayout.isRefreshing = it
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.i(TAG, "onMessageError $it")
        incLayoutError.visibility = View.VISIBLE
        incLayoutEmpty.visibility = View.GONE
        textViewError.text = "Error $it"
    }

    private val emptyObserver = Observer<Boolean> {
        Log.i(TAG, "emptyListObserver $it")
        incLayoutEmpty.visibility = View.VISIBLE
        incLayoutError.visibility = View.GONE
    }

}
