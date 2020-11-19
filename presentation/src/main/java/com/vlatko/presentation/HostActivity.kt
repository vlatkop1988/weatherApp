package com.vlatko.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.vlatko.presentation.base.BaseActivity
import com.vlatko.presentation.base.HostActivityViewModel
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : BaseActivity() {

    private lateinit var viewModel: HostActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        val factory: ViewModelProvider.Factory = NewInstanceFactory()
        viewModel = ViewModelProvider(this, factory).get(HostActivityViewModel::class.java)

        viewModel.getCurrentWeather("Novi Sad",{
            Toast.makeText(this, it.name,Toast.LENGTH_LONG).show()
            tvCity.text = it.main?.temp.toString()

        },{
            Toast.makeText(this, it,Toast.LENGTH_LONG).show()
        })

    }
}