package com.shrutiapp.views

import android.Manifest
import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shrutiapp.R
import com.shrutiapp.databinding.ActivityFullScreenPermissionBinding

class FullScreenPermission : AppCompatActivity() {
    lateinit var binding: ActivityFullScreenPermissionBinding
    val STORAGE = 11
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initSetup()
        clickEvents()
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                intent = Intent(this@FullScreenPermission, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else if (ContextCompat.checkSelfPermission(
                this@FullScreenPermission,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            intent = Intent(this@FullScreenPermission, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun clickEvents() {
        binding.allowBtn.setOnClickListener(View.OnClickListener {
            ObjectAnimator.ofFloat<View>(it, View.ALPHA, 0.4f, 1.0f).setDuration(1000).start()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestRuntimePermissionFunc("manageStorage");
            } else {
                requestRuntimePermissionFunc("storage");
            }
        })

        binding.denyBtn.setOnClickListener(View.OnClickListener {
            ObjectAnimator.ofFloat<View>(it, View.ALPHA, 0.4f, 1.0f).setDuration(1000).start()
        })
    }

    private fun requestRuntimePermissionFunc(permissionName: String) {
        if (permissionName == "manageStorage") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    intent = Intent(this@FullScreenPermission, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.setData(uri)
                    startActivity(intent)
                }
            }
        } else if (permissionName == "storage") {
            if (ContextCompat.checkSelfPermission(
                    this@FullScreenPermission,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                intent = Intent(this@FullScreenPermission, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@FullScreenPermission,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                val builder: android.app.AlertDialog.Builder =
                    android.app.AlertDialog.Builder(this@FullScreenPermission)
                builder.setMessage("this permission is required")
                    .setTitle("Allow storage permission to use our app")
                    .setCancelable(false)
                    .setPositiveButton("Allow",
                        DialogInterface.OnClickListener { dialog, which ->
                            ActivityCompat.requestPermissions(
                                this@FullScreenPermission,
                                arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
                                STORAGE
                            )
                        })
                    .setNegativeButton("Deny") { dialog, which -> dialog.dismiss() }
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    this@FullScreenPermission,
                    arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE
                )
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            intent = Intent(this@FullScreenPermission, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else if (!ActivityCompat.shouldShowRequestPermissionRationale(
                this@FullScreenPermission,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            val builder = AlertDialog.Builder(this@FullScreenPermission)
            builder.setMessage("this feature is unavailable , open settings")
                .setTitle("Allow storage permission to use our app")
                .setCancelable(false)
                .setPositiveButton("Allow") { dialog: DialogInterface, which: Int ->
                    val intent =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.setData(uri)
                    startActivity(intent)
                    dialog.dismiss()
                }
                .setNegativeButton(
                    "Deny"
                ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                .show()
        } else {
            requestRuntimePermissionFunc("storage")
        }

    }

    private fun initSetup() {


    }
}