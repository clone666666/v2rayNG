package com.v2ray.ang.ui

import android.Manifest
import android.content.*
import com.tbruyelle.rxpermissions.RxPermissions
import com.v2ray.ang.R
import com.v2ray.ang.util.AngConfigManager
import android.os.Bundle
import org.jetbrains.anko.*

class ScScannerActivity : BaseActivity() {
    companion object {
        private const val REQUEST_SCAN = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.abc_list_menu_item_layout)
        importQRcode(REQUEST_SCAN)
    }

    fun importQRcode(requestCode: Int): Boolean {
        RxPermissions(this)
                .request(Manifest.permission.CAMERA)
                .subscribe {
                    if (it)
                        startActivityForResult<ScannerActivity>(requestCode)
                    else
                        toast(R.string.toast_permission_denied)
                }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_SCAN ->
                if (resultCode == RESULT_OK) {
                    val count = AngConfigManager.importBatchConfig(data?.getStringExtra("SCAN_RESULT"), "")
                    if (count > 0) {
                        toast(R.string.toast_success)
                    } else {
                        toast(R.string.toast_failure)
                    }
                    startActivity<MainActivity>()
                }
        }
        finish()
    }

}