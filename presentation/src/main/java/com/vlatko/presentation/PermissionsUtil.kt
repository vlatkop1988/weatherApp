package com.vlatko.presentation

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object PermissionsUtil {

    fun checkAndRequest(
        activity: FragmentActivity,
        fragment: Fragment,
        requestCode: Int,
        granted: (() -> Unit)?,
        vararg permissions: String
    ) {
        var result = 0
        for (p in permissions) {
            result += ContextCompat.checkSelfPermission(activity, p)
        }
        if (result == 0) {
            granted?.invoke()
        } else {
            fragment.requestPermissions(permissions, requestCode)
        }
    }

    fun checkResult(grantResults: IntArray, granted: (() -> Unit)?) {
        var result = 0
        grantResults.forEach { result += it }
        if (result == PackageManager.PERMISSION_GRANTED) granted?.invoke()
    }
}