package com.yunusbedir.havadurumu.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yunusbedir.havadurumu.Model.Region
import com.yunusbedir.havadurumu.Model.User
import com.yunusbedir.havadurumu.Model.weather.BaseWeather

import com.yunusbedir.havadurumu.R
import com.yunusbedir.havadurumu.ViewModel.CurrentViewModel
import com.yunusbedir.havadurumu.extensions.extSetBackGround
import kotlinx.android.synthetic.main.fragment_current.*
import kotlinx.android.synthetic.main.layout_error.*

/**
 * A simple [Fragment] subclass.
 */
class CurrentFragment : Fragment() {
    private val TAG = this.javaClass.simpleName
    private lateinit var viewModel: CurrentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupViewModel()
        return inflater.inflate(R.layout.fragment_current, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadUser()
        swipeRefreshLayout.setOnRefreshListener(swipeRefreshLayoutListener)
        fabSettings.setOnClickListener(fabSettingsOnClickListener)
        super.onViewCreated(view, savedInstanceState)
    }

    private val fabSettingsOnClickListener = View.OnClickListener {
        val action = CurrentFragmentDirections.actionCurrentFragmentToSettingsFragment()
        Navigation.findNavController(it).navigate(action)
    }

    private val swipeRefreshLayoutListener = SwipeRefreshLayout.OnRefreshListener {
        incLayoutError.visibility = View.GONE
        incLayoutEmpty.visibility = View.GONE
        viewModel.loadUser()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(CurrentViewModel::class.java)
        viewModel.weather.observe(viewLifecycleOwner, renderWeather)
        viewModel.user.observe(viewLifecycleOwner, renderUser)
        viewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        viewModel.isEmptyList.observe(viewLifecycleOwner, emptyListObserver)
    }

    private val renderWeather = Observer<BaseWeather> { baseWeather ->
        Log.v(TAG, "data updated $baseWeather")
        incLayoutError.visibility = View.GONE
        incLayoutEmpty.visibility = View.GONE
        tvMain.text = baseWeather.current?.weather?.get(0)?.description.toString().toUpperCase()
        tvFeelsLike.text = baseWeather.current?.feelsLike?.toInt().toString()
        tvTemp.text = baseWeather.current?.temp?.toInt().toString()
        tvWindDeg.text = baseWeather.current?.windDeg?.toString()
        tvHumidity.text = baseWeather.current?.humidity?.toString()
        tvPressure.text = baseWeather.current?.pressure?.toString()
        tvUvi.text = baseWeather.current?.uvi?.toString()
        tvWindSpeed.text = baseWeather.current?.windSpeed?.toString()
        tvClouds.text = baseWeather.current?.clouds?.toString()
        baseLayout.extSetBackGround(
            baseWeather.current?.weather?.get(0)?.main.toString().toLowerCase()
        )

    }

    private val renderUser = Observer<User> { user ->
        Log.v(TAG, "data updated $user")
        viewModel.loadCurrentWeather(user.region)
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

    private val emptyListObserver = Observer<Boolean> {
        Log.i(TAG, "emptyListObserver $it")
        incLayoutEmpty.visibility = View.VISIBLE
        incLayoutError.visibility = View.GONE
    }


}
