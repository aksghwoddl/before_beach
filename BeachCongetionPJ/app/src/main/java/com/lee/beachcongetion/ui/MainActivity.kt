package com.lee.beachcongetion.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lee.beachcongetion.R
import com.lee.beachcongetion.databinding.ActivityMainBinding
import com.lee.beachcongetion.ui.fragment.AllBeachesFragment

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var binding : ActivityMainBinding
    private lateinit var mAllBeachFragment : AllBeachesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
//        getHashKey()
        savedInstanceState?:let {
            mAllBeachFragment = AllBeachesFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.container, mAllBeachFragment).commit()
        }
    }

    /**
     *  Functions for get kakao map HashKey
     * **/
    /*private fun getHashKey() {
        var packageInfo : PackageInfo = PackageInfo()
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException){
            e.printStackTrace()
        }

        for (signature: Signature in packageInfo.signatures){
            try{
                var md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEY_HASH", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch(e: NoSuchAlgorithmException){
                Log.e("KEY_HASH", "Unable to get MessageDigest. signature = " + signature, e)
            }
        }
    }*/
}