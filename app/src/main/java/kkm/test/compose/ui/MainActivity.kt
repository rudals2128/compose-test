package kkm.test.compose.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import dagger.hilt.android.AndroidEntryPoint
import kkm.test.compose.ui.theme.ComposeTheme
import kkm.test.compose.ui.view.MainScreen
import java.security.MessageDigest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
             ComposeTheme{
                MainScreen()
            }
        }


        getHashKey(this)

    }

    fun getHashKey(context: Context): String? {
        val packageName = context.packageName
        try {
            val packageInfo = context.packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in packageInfo.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("kkm", " 해시키 = ${Base64.encodeToString(md.digest(), Base64.DEFAULT)}")
                return Base64.encodeToString(md.digest(), Base64.DEFAULT)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}