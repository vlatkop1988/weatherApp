package com.vlatko.presentation.fragments

import android.Manifest
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.vlatko.presentation.PermissionsUtil
import com.vlatko.presentation.R
import com.vlatko.presentation.base.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_weather_overview.*

class WeatherOverviewFragment : BaseFragment() {

    private val permissionsRequestCode = 2002

    private lateinit var viewModel: WeatherOverviewFragmentViewModel
    lateinit var compositeDisposable: CompositeDisposable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_weather_overview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherOverviewFragmentViewModel::class.java)
        compositeDisposable = CompositeDisposable()



        if ( arguments?.getBoolean("fromFavorites") == true){
            showDataForSelectedLocation(arguments?.getString("cityName") ?: "")
        }else {
            PermissionsUtil.checkAndRequest(
                requireActivity(),
                permissionsRequestCode,
                ::showDataForCurrentLocation,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }

        ibAdd.setOnClickListener {
            findNavController().navigate(R.id.action_weatherOverviewFragment_to_favoritesFragment)
        }

        ibSettings.setOnClickListener {
        }

        btnSwitchTheme.setOnClickListener {

            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Configuration.UI_MODE_NIGHT_NO ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun setWeatherIcon(iconId: String?) {
        Glide.with(this)
            .load("https://openweathermap.org/img/w/$iconId.png")
            .into(ivWeatherIcon)
    }

    private fun showDataForCurrentLocation() {
        viewModel.getWeatherForCurrentLocation()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                tvCity.text = it.name
                tvTemperature.text = "${ it.main?.temp?.toInt().toString()}º"
                tvCondition.text = it.weather?.firstOrNull()?.main
                tvConditionDesc.text = it.weather?.firstOrNull()?.description
                setWeatherIcon(it.weather?.firstOrNull()?.icon)
            }, {
                Toast.makeText(requireContext(), viewModel.parseError(it), Toast.LENGTH_LONG).show()
            }).addTo(compositeDisposable)
    }


    private fun showDataForSelectedLocation(cityName : String) {
        viewModel.getWeatherByCityName(cityName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                tvCity.text = it.name
                tvTemperature.text = "${ it.main?.temp?.toInt().toString()}º"
                tvCondition.text = it.weather?.firstOrNull()?.main
                tvConditionDesc.text = it.weather?.firstOrNull()?.description
                setWeatherIcon(it.weather?.firstOrNull()?.icon)
            }, {
                Toast.makeText(requireContext(), viewModel.parseError(it), Toast.LENGTH_LONG).show()
            }).addTo(compositeDisposable)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionsRequestCode -> PermissionsUtil.checkResult(grantResults, ::showDataForCurrentLocation)
        }
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }
}