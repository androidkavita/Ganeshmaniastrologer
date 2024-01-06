package com.maxtra.callastro.baseClass

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.callastro.R
import com.maxtra.astrorahiastrologer.util.Utils

import com.google.android.material.snackbar.Snackbar
import com.maxtra.callastro.prefs.UserPref

//import com.razorpay.PaymentData
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var userPref: UserPref

    @Inject
    lateinit var utils: Utils

//    @Inject
//    lateinit var chooseLanguageViewModelFactory:  ChooseLanguageViewModelFactory

    var dialog: Dialog?=null
    var progressDialog: ProgressDialog? = null
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        userPref=UserPref(this)
    }

    protected fun showProgressDialog() {
        if (dialog == null)
            dialog = Dialog(this)
        dialog!!.setContentView(R.layout.progress_dialog)
        dialog!!.setCancelable(false)

        if (dialog != null && !dialog!!.isShowing)
            dialog!!.show()
    }

    protected fun hideProgressDialog() {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
    }

    /*fun snackBar(message: String){
        Snackbar.make(findViewById(R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }*/

    protected fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flContent, fragment, fragment.javaClass.name).commit()
    }

    fun changeLanguage( prefLanguage:String)
    {
        val locale = Locale(prefLanguage)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.getDisplayMetrics())
    }
    fun snackbar(message: String){
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    fun toast(context : Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

//    open fun onPaymentSuccess(p0: String?, p1: PaymentData?) {}
}
