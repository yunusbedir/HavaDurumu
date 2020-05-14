package com.yunusbedir.havadurumu.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yunusbedir.havadurumu.Model.BaseWeather

import com.yunusbedir.havadurumu.R
import com.yunusbedir.havadurumu.ViewModel.CurrentViewModel
import kotlinx.android.synthetic.main.fragment_current.*

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
        /*
        layoutError.visibility=View.GONE
        layoutEmpty.visibility=View.GONE
        adapter.update(it)*/
        tv_main.text = baseWeather.current?.weather?.get(0)?.main
        tv_feels_like.text = baseWeather.current?.feelsLike?.toInt().toString()
        tv_temp.text = baseWeather.current?.temp?.toInt().toString()
        tv_wind_deg.text = baseWeather.current?.windDeg?.toString()
        tv_humidity.text = baseWeather.current?.humidity?.toString()
        tv_pressure.text = baseWeather.current?.pressure?.toString()
        tv_uvi.text = baseWeather.current?.uvi?.toString()
        tv_wind_speed.text = baseWeather.current?.windSpeed?.toString()
        tv_clouds.text = baseWeather.current?.clouds?.toString()


    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        /*val visibility=if(it)View.VISIBLE else View.GONE
        progressBar.visibility= visibility*/
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        /*layoutError.visibility=View.VISIBLE
        layoutEmpty.visibility=View.GONE
        textViewError.text= "Error $it"*/
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")/*
        layoutEmpty.visibility=View.VISIBLE
        layoutError.visibility=View.GONE*/
    }


}
