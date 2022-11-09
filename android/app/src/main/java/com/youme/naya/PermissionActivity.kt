package com.youme.naya

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import com.youme.naya.ui.theme.AndroidTheme

class PermissionActivity : BaseActivity(TransitionMode.NONE) {

    private var permissionList = listOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.NFC,
        Manifest.permission.INTERNET,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidTheme {
                checkPermission()
            }
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        for (permission in permissionList) {
            if (checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permissionList.toTypedArray(), 0)
            }
            Log.i("Permission Check", "$permission processing")
        }
    }
}