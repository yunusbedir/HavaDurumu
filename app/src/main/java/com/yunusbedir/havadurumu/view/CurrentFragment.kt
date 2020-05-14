package com.yunusbedir.havadurumu.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yunusbedir.havadurumu.Model.BaseWeather

import com.yunusbedir.havadurumu.R
import com.yunusbedir.havadurumu.ViewModel.CurrentViewModel
import com.yunusbedir.havadurumu.util.extImageLoad
import kotlinx.android.synthetic.main.fragment_current.*
import kotlinx.android.synthetic.main.layout_error.*

/**
 * A simple [Fragment] subclass.
 */
class CurrentFragment : Fragment() {
    private val TAG = "CurrentFragment"
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
        viewModel.loadCurrentWeather()
        swipeRefreshLayout.setOnRefreshListener {
            incLayoutError.visibility = View.GONE
            incLayoutEmpty.visibility = View.GONE
            viewModel.loadCurrentWeather()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(CurrentViewModel::class.java)
        viewModel.weather.observe(viewLifecycleOwner, renderWeather)
        viewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        viewModel.isEmptyList.observe(viewLifecycleOwner, emptyListObserver)
    }

    private val renderWeather = Observer<BaseWeather> { baseWeather ->
        Log.v(TAG, "data updated $baseWeather")
        incLayoutError.visibility = View.GONE
        incLayoutEmpty.visibility = View.GONE
        tvMain.text = baseWeather.current?.weather?.get(0)?.main
        tvFeelsLike.text = baseWeather.current?.feelsLike?.toInt().toString()
        tvTemp.text = baseWeather.current?.temp?.toInt().toString()
        tvWindDeg.text = baseWeather.current?.windDeg?.toString()
        tvHumidity.text = baseWeather.current?.humidity?.toString()
        tvPressure.text = baseWeather.current?.pressure?.toString()
        tvUvi.text = baseWeather.current?.uvi?.toString()
        tvWindSpeed.text = baseWeather.current?.windSpeed?.toString()
        tvClouds.text = baseWeather.current?.clouds?.toString()
        imgIcon.extImageLoad("https://openweathermap.org/img/wn/${baseWeather.current?.weather?.get(0)?.icon}@2x.png")


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
