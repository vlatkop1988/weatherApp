package com.vlatko.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.vlatko.presentation.base.BaseActivity
import com.vlatko.presentation.base.HostActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : BaseActivity() {

    private lateinit var viewModel: HostActivityViewModel
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
    }

    override fun onResume() {
        super.onResume()
        val factory: ViewModelProvider.Factory = NewInstanceFactory()
        viewModel = ViewModelProvider(this, factory).get(HostActivityViewModel::class.java)

        compositeDisposable = CompositeDisposable()
        viewModel.getCurrentWeather("Novi Sad")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                tvCity.text = it.name
            }, {
                Toast.makeText(this, viewModel.parseError(it), Toast.LENGTH_LONG).show()
            }).addTo(compositeDisposable)
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }
}