package com.callastro.baseClass

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.callastro.R
import com.callastro.data.ApiService
import com.maxtra.astrorahiastrologer.util.Utils
import com.google.android.material.snackbar.Snackbar
import com.maxtra.callastro.prefs.UserPref
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
open class BaseFragment :  Fragment() {
    @Inject
    lateinit var userPref: UserPref

    @Inject
    lateinit var utils: Utils

    @Inject
    lateinit var apiService : ApiService

    var progressDialog: ProgressDialog? = null
    var dialog: Dialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    /*   protected fun replaceFragment(fragment: Fragment) {
           val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
           fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
           fragmentTransaction.replace(R.id.frame_Container, fragment, fragment.javaClass.name).commit()
       }*/

    fun setFragment(fragment: Fragment, removeStack: Boolean, activity: FragmentActivity, mContainer: Int) {
        val fragmentManager = activity.supportFragmentManager
        val ftTransaction = fragmentManager.beginTransaction()
        if (removeStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            ftTransaction.setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit,R.anim.enter, R.anim.exit)
            ftTransaction.replace(mContainer, fragment)
            ftTransaction.addToBackStack(null)
        } else {
            ftTransaction.replace(mContainer, fragment)
            ftTransaction.addToBackStack(null)
        }
        ftTransaction.commit()
    }

    protected fun showProgressDialog() {
        if (dialog == null)
            dialog = Dialog(requireActivity())
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

    fun snackBar(view : View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun toast(context : Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}