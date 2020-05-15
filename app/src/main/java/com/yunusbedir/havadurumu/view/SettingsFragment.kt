package com.yunusbedir.havadurumu.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.yunusbedir.havadurumu.Model.Region
import com.yunusbedir.havadurumu.Model.User

import com.yunusbedir.havadurumu.R
import com.yunusbedir.havadurumu.ViewModel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_current.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.layout_error.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {
    private val AUTOCOMPLATE_REQUEST_CODE = 1001
    private val TAG = "SettingsFragment"
    private val user = User()
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
        buttonOpenSearch.setOnClickListener {
            val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY,
                mutableListOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            )
                .setTypeFilter(TypeFilter.REGIONS)
                .setCountries(listOf("TR"))
                .build(context!!)

            startActivityForResult(intent, AUTOCOMPLATE_REQUEST_CODE)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLATE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val place = data?.let { Autocomplete.getPlaceFromIntent(it) }
                place?.let {
                    user.region = Region(
                        it.name,
                        it.latLng?.latitude?.toInt(),
                        it.latLng?.longitude?.toInt()
                    )
                }
                Log.i(TAG, place?.name + "+" + place?.latLng)
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(data!!)
                Log.i(TAG, "" + status.statusMessage)
            }
        }
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
        //incLayoutError.visibility = View.GONE
        //incLayoutEmpty.visibility = View.GONE
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.i(TAG, "isViewLoading $it")
        // swipeRefreshLayout.isRefreshing = it
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.i(TAG, "onMessageError $it")
        //incLayoutError.visibility = View.VISIBLE
        ///incLayoutEmpty.visibility = View.GONE
        //textViewError.text = "Error $it"
    }

    private val emptyObserver = Observer<Boolean> {
        Log.i(TAG, "emptyListObserver $it")
        //incLayoutEmpty.visibility = View.VISIBLE
        //incLayoutError.visibility = View.GONE
    }

}
