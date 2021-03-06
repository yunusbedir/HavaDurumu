package com.yunusbedir.havadurumu.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.yunusbedir.havadurumu.Data.DataRepository
import com.yunusbedir.havadurumu.Data.OperationCallBack
import com.yunusbedir.havadurumu.Model.Region
import com.yunusbedir.havadurumu.Model.User
import com.yunusbedir.havadurumu.Model.enums.Language
import com.yunusbedir.havadurumu.Model.enums.Units

import com.yunusbedir.havadurumu.R
import com.yunusbedir.havadurumu.ViewModel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {
    private val TAG = this.javaClass.simpleName
    private val AUTOCOMPLETE_REQUEST_CODE = 1001


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
        buttonOpenSearch.setOnClickListener(buttonRegionClickListener)
        buttonUpdate.setOnClickListener(buttonUpdateClickListener)
        radioGroupLanguage.setOnCheckedChangeListener(radioGroupLanguageListener)
        radioGroupUnits.setOnCheckedChangeListener(radioGroupUnitsListener)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
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
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)

    }

    private val radioGroupLanguageListener =
        RadioGroup.OnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rglTurkish -> {
                    user.lang = Language.Turkish.text
                }
                R.id.rglEnglish -> {
                    user.lang = Language.English.text
                }
            }
        }

    private val radioGroupUnitsListener =
        RadioGroup.OnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rguFahrenheit -> {
                    user.units = Units.Fahrenheit.text
                }
                R.id.rguCelsius -> {
                    user.units = Units.Celsius.text
                }
                R.id.rguKelvin -> {
                    user.units = Units.Kelvin.text
                }
            }
        }

    private val buttonRegionClickListener = View.OnClickListener {
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY,
            mutableListOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        )
            .setTypeFilter(TypeFilter.REGIONS)
            .setCountries(listOf("TR"))
            .build(context!!)

        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    private val buttonUpdateClickListener = View.OnClickListener {
        DataRepository(context!!).updateUser(user, object : OperationCallBack<Boolean> {
            override fun onSuccess(data: Boolean?) {
                Log.i(TAG, "Succes update User")

            }

            override fun onError(error: Throwable?) {
                Log.i(TAG, "unsucces update user")
            }

        })

    }

    private val renderUser = Observer<User> { user ->

        when (user.lang) {
            Language.Turkish.text -> {
                radioGroupLanguage.check(rglTurkish.id)
            }
            Language.English.text -> {
                radioGroupLanguage.check(rglEnglish.id)
            }
            Language.Japanese.text -> {
//                radioGroupLanguage.check(rglTurkish.id)
            }
        }

        when (user.units) {
            Units.Fahrenheit.text -> {
                radioGroupUnits.check(rguFahrenheit.id)
            }
            Units.Celsius.text -> {
                radioGroupUnits.check(rguCelsius.id)
            }
            Units.Kelvin.text -> {
                radioGroupUnits.check(rguKelvin.id)
            }
        }

        user.region?.let {
            buttonOpenSearch.text = it.name
        }

    }

    private val onMessageErrorObserver = Observer<Throwable> {
        if (it.message.toString().toLowerCase().contains("empty")) {
            Log.i(TAG, "onMessageError : user boş , ${it.message}")
        } else {
            Log.i(TAG, "onMessageError ${it.message}")
        }
    }

}
