package com.vlatko.presentation.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vlatko.presentation.PermissionsUtil
import com.vlatko.presentation.R
import com.vlatko.presentation.base.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers

class SplashFragment : BaseFragment() {
    private val permissionsRequestCode = 2002

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_splash, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myThread = object : Thread() {
            override fun run() {
                try {
                    sleep(1000)
                    PermissionsUtil.checkAndRequest(
                        requireActivity(),
                        this@SplashFragment,
                        permissionsRequestCode,
                        {
                            AndroidSchedulers.mainThread().createWorker().schedule {
                                findNavController().navigate(R.id.action_splashFragment_to_weatherOverviewFragment2)
                            }
                        },
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        myThread.start()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionsRequestCode -> PermissionsUtil.checkResult(
                grantResults
            ) {
                findNavController().navigate(R.id.action_splashFragment_to_weatherOverviewFragment2)
            }
        }
    }
}