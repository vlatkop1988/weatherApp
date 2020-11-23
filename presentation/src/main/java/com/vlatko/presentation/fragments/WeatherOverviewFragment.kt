package com.vlatko.presentation.fragments

import android.annotation.SuppressLint
import android.content.res.Configuration.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.vlatko.domain.models.CurrentWeather
import com.vlatko.presentation.R
import com.vlatko.presentation.base.BaseFragment
import com.vlatko.presentation.ui.visible
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_weather_overview.*

class WeatherOverviewFragment : BaseFragment() {

    private lateinit var viewModel: WeatherOverviewFragmentViewModel
    private lateinit var compositeDisposable: CompositeDisposable

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

        when {
            viewModel.getTheme() == AppCompatDelegate.MODE_NIGHT_NO -> setNightMode()
            viewModel.getTheme() == AppCompatDelegate.MODE_NIGHT_YES -> setDayMode()
            else -> checkCurrentMode()
        }

        when {
            viewModel.getUnit() == METRIC_UNIT -> setMetric()
            viewModel.getUnit() == IMPERIAL_UNIT -> setImperial()
        }
        showData()
        ibAdd.setOnClickListener {
            findNavController().navigate(R.id.action_weatherOverviewFragment_to_favoritesFragment)
        }

        btnSwitchTheme.setOnClickListener {
            checkCurrentMode()
        }

        btnSwitchUnit.setOnClickListener {
            when (viewModel.getUnit()) {
                METRIC_UNIT -> {
                    setImperial()
                    viewModel.storeUnit(IMPERIAL_UNIT)
                    showData()
                }
                IMPERIAL_UNIT -> {
                    setMetric()
                    viewModel.storeUnit(METRIC_UNIT)
                    showData()
                }
            }
        }
        crossFade()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
        }
    }

    private fun crossFade() {
        tvTemperature.apply {
            alpha = 0f
            visible()
            animate()
                .alpha(1f)
                .setDuration(3000L)
                .setListener(null)
        }
    }

    private fun checkCurrentMode() {
        when (resources.configuration.uiMode and UI_MODE_NIGHT_MASK) {
            UI_MODE_NIGHT_YES -> {
                setNightMode()
                viewModel.storeTheme(AppCompatDelegate.MODE_NIGHT_NO)
            }
            UI_MODE_NIGHT_NO -> {
                setDayMode()
                viewModel.storeTheme(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun setDayMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        btnSwitchTheme.text = getString(R.string.label_day_mode)
    }

    private fun setNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        btnSwitchTheme.text = getString(R.string.label_night_mode)
    }

    private fun setMetric() {
        btnSwitchUnit.text = getString(R.string.label_imperial)
    }

    private fun setImperial() {
        btnSwitchUnit.text = getString(R.string.label_metric)
    }

    private fun setWeatherIcon(iconId: String?) {
        Glide.with(this)
            .load("${getString(R.string.image_url)}$iconId.png")
            .into(ivWeatherIcon)
    }

    private fun showData() {
        if (arguments?.getBoolean("fromFavorites") == true) {
            showDataForSelectedLocation(arguments?.getString("cityName") ?: "")
        } else showDataForCurrentLocation()
    }


    private fun showDataForCurrentLocation() {
        viewModel.getWeatherForCurrentLocation()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setData(it)
            }, {
                Toast.makeText(requireContext(), viewModel.parseError(it), Toast.LENGTH_LONG).show()
            }).addTo(compositeDisposable)
    }


    private fun showDataForSelectedLocation(cityName: String) {
        viewModel.getWeatherByCityName(cityName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setData(it)
            }, {
                Toast.makeText(requireContext(), viewModel.parseError(it), Toast.LENGTH_LONG).show()
            }).addTo(compositeDisposable)
    }

    @SuppressLint("SetTextI18n")
    private fun setData(currentWeather: CurrentWeather) {
        tvCity.text = currentWeather.name
        tvTemperature.text = "${
            currentWeather.main?.temp?.toInt().toString()
        }${
            if (viewModel.getUnit() == "metric") getString(R.string.label_metric) else getString(
                R.string.label_imperial
            )
        }"
        tvConditionDesc.text = currentWeather.weather?.firstOrNull()?.description
        setWeatherIcon(currentWeather.weather?.firstOrNull()?.icon)
        tvFeelsLike.text =
            "${getString(R.string.label_real_feel)}: ${currentWeather.main?.feelsLike?.toInt()}${
                if (viewModel.getUnit() == "metric") getString(R.string.label_metric)
                else getString(R.string.label_imperial)
            }"

    }

    companion object {
        private const val METRIC_UNIT = "metric"
        private const val IMPERIAL_UNIT = "imperial"
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }
}