package com.vlatko.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.vlatko.presentation.base.BaseFragment
import com.vlatko.presentation.base.WeatherOverviewFragmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_weather_overview.*

class WeatherOverviewFragment : BaseFragment() {

    private lateinit var viewModel: WeatherOverviewFragmentViewModel
    lateinit var compositeDisposable: CompositeDisposable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_weather_overview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherOverviewFragmentViewModel::class.java)
        compositeDisposable = CompositeDisposable()
        viewModel.getCurrentWeather()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                tvCity.text = it.name
            }, {
                Toast.makeText(requireContext(), viewModel.parseError(it), Toast.LENGTH_LONG).show()
            }).addTo(compositeDisposable)
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }
}