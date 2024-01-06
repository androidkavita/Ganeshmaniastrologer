package com.callastro

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.callastro.databinding.ActivityMainBinding
import com.callastro.ui.activities.*
import com.callastro.ui.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.maxtra.callastro.baseClass.BaseActivity
import com.maxtra.callastro.prefs.UserPref
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity(),  NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener  {
    lateinit var binding: ActivityMainBinding
    lateinit var profilepic: ImageView
    lateinit var tvUserName: TextView
    lateinit var tv_email: TextView
    lateinit var bottomdialog: BottomSheetDialog
    var id:String = ""
    var otp:String = ""
    var is_new:String = ""
    var appRunningBackground:Boolean = false
    private val PERMISSION_REQ_ID = 22
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onStart() {
        super.onStart()
        //binding.bottomNav.menu.clear()
        setNavigationData()
        // setNavigationBar()
        // initializeUsersBnv()

    }
    val REQUESTED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_PHONE_STATE
    )

    private fun checkSelfPermission(): Boolean {
        return !(ContextCompat.checkSelfPermission(
            this,
            REQUESTED_PERMISSIONS[0]
        ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    this,
                    REQUESTED_PERMISSIONS[1]
                ) != PackageManager.PERMISSION_GRANTED)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)

        if (intent!=null){
            id = intent.getStringExtra("id").toString()
            otp = intent.getStringExtra("otp").toString()
            is_new = intent.getStringExtra("is_new").toString()
        }

//        val runningAppProcessInfo = RunningAppProcessInfo()
//        ActivityManager.getMyMemoryState(runningAppProcessInfo)
//        appRunningBackground =
//            runningAppProcessInfo.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND
//        if (appRunningBackground) {
//            Toast.makeText(
//                applicationContext,
//                "Your Android Application is Running in " + "Background",
//                Toast.LENGTH_SHORT
//            ).show()
//        } else {
//            Toast.makeText(
//                applicationContext,
//                "Your Android Application is not Running in " + "Foreground",
//                Toast.LENGTH_SHORT
//            ).show()
//        }


        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        replaceFragment(HomeFragment())
        userPref= UserPref(this)
        binding.drawerToolbar.ivMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID)
        }

        binding.drawerToolbar.ivHeaderpic.setOnClickListener {
            startActivity(Intent(this@MainActivity,ViewProfile::class.java))
        }
//        if (userPref.get_is_new() == "1"){
//            dashboardbottomsheet()
            // toast("1")

//        }
//        else if (userPref.get_is_new() == "0"){
            //  toast("0")
//        }


        /*  val navView: NavigationView = binding.nvView
           val header: View = navView.getHeaderView(0)
           tvUserName =header.findViewById(R.id.tvUserName)
           profilepic =header.findViewById(R.id.iv_imgUser)
           tv_email= header.findViewById(R.id.tv_email)


           Glide.with(this).load(userPref.getProfileImage())
               .apply(RequestOptions.placeholderOf(R.drawable.user_image_place_holder))
               .apply(RequestOptions.errorOf(R.drawable.user_image_place_holder))
               .into(profilepic)
   */



        // Glide.with(this).load(userPref.getProfileImage()).into(profilepic)

//        Glide.with(this).load(userPref.getProfileImage())
//            .apply(RequestOptions.placeholderOf(R.drawable.user_image_place_holder))
//            .apply(RequestOptions.errorOf(R.drawable.user_image_place_holder))
//            .into(profilepic)



        /* Glide.with(this).load(userPref.getProfileImage())
             .apply(RequestOptions.placeholderOf(R.drawable.user_image_place_holder))
             .apply(RequestOptions.errorOf(R.drawable.user_image_place_holder))
             .into(profilepic)*/


        /*  if (!userPref.getProfileImage().isNullOrBlank()) {
              Glide.with(this).load(Uri.parse(userPref.getProfileImage()))
                  .apply(RequestOptions.placeholderOf(R.drawable.user_image_place_holder))
                  .apply(RequestOptions.errorOf(R.drawable.user_image_place_holder))
                  .into(profilepic)
          }*/





        // tv_email.setText(userPref.getEmail())


    }
//
//    override fun onStop() {
//        super.onStop()
//        Toast.makeText(
//            applicationContext,
//            "Your Android Application is Running in " + "Background",
//            Toast.LENGTH_SHORT
//        ).show()
//    }
//    override fun onDestroy() {
//        super.onDestroy()
//
//        Toast.makeText(
//            applicationContext,
//            "Your Android Application is Running in " + "Background",
//            Toast.LENGTH_SHORT
//        ).show()
//    }

    @SuppressLint("SetTextI18n")
    private fun setNavigationData() {
        val navView: NavigationView = binding.nvView
        val header: View = navView.getHeaderView(0)
        tvUserName =header.findViewById(R.id.tvUserName)
        profilepic =header.findViewById(R.id.iv_imgUser)
        tv_email= header.findViewById(R.id.tv_email)

        Glide.with(this).load(userPref.getProfileImage())
            .apply(RequestOptions.placeholderOf(R.drawable.user_image_place_holder))
            .apply(RequestOptions.errorOf(R.drawable.user_image_place_holder))
            .into(profilepic)

//        binding.header.tvUserName.text = userPref.getSubUserName()
//        binding.header.tvEmail.text = userPref.getEmail()
//
//        if (!userPref.getUserProfileImage().isNullOrBlank()) {
//            Glide.with(this).load(Uri.parse(userPref.getUserProfileImage()))
//                .apply(RequestOptions.placeholderOf(R.drawable.user_image_place_holder))
//                .apply(RequestOptions.errorOf(R.drawable.user_image_place_holder))
//                .into(binding.header.imgUser)
//        }



        if(userPref.getName().equals(null)){
            tvUserName.setText("...")
        }
        else{tvUserName.setText(userPref.getName())}



        if(userPref.getEmail().equals(null)){
            tv_email.setText("...")
        }
        else{tv_email.setText(userPref.getEmail())}


        var nav_myprofile: RelativeLayout = header.findViewById(R.id.rl_myprofile)
        var nav_booking: RelativeLayout = header.findViewById(R.id.rl_mybookings)
        var nav_chatHistory: RelativeLayout = header.findViewById(R.id.rl_chatHistory)
        var nav_callHistory: RelativeLayout = header.findViewById(R.id.rl_callHistory)
        var nav_customersupport: RelativeLayout = header.findViewById(R.id.rl_customersupport)
        var nav_settings: RelativeLayout = header.findViewById(R.id.rl_settings)
        var nav_faq: RelativeLayout = header.findViewById(R.id.rl_faq)
        var nav_aboutus: RelativeLayout = header.findViewById(R.id.rl_aboutus)
        var nav_rateapp: RelativeLayout = header.findViewById(R.id.rl_rateapp)
        var nav_shareapp: RelativeLayout = header.findViewById(R.id.rl_shareapp)
        var nav_logout: RelativeLayout = header.findViewById(R.id.rl_logout)

        nav_aboutus.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            var intent = Intent(this,AboutusActivity::class.java)
            startActivity(intent)
        }
        nav_customersupport.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            var intent = Intent(this,Customer_Support::class.java)
            startActivity(intent)
        }
        nav_faq.setOnClickListener {
            val intent = Intent (this, FaqActivity::class.java)
            startActivity(intent)
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        nav_logout.setOnClickListener {
            logout()
        }
        nav_myprofile.setOnClickListener {
            val intent = Intent (this, ViewProfile::class.java)
            startActivity(intent)
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        nav_settings.setOnClickListener {
            val intent = Intent (this, SettingsActivity::class.java)
            startActivity(intent)
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        nav_booking.setOnClickListener {
            val intent = Intent (this, MyBookingsActivity::class.java)
            startActivity(intent)
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }



        nav_callHistory.setOnClickListener {
            val intent = Intent (this, CallHistoryActivity::class.java)
            startActivity(intent)
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }


        nav_chatHistory.setOnClickListener {
            val intent = Intent (this, ChatHistoryActivity::class.java)
            startActivity(intent)
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }

    }

    fun logout() {
        // on below line we are creating a new bottom sheet dialog.
        bottomdialog = BottomSheetDialog(this)
        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.logout_bottomsheet, null)
        val metrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(metrics)
        bottomdialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomdialog.behavior.peekHeight = metrics.heightPixels
        var btnLogout = view.findViewById<Button>(R.id.btnLogout)
        var btnNo = view.findViewById<Button>(R.id.btnNo)
        btnLogout.setOnClickListener {
            userPref.clearPref()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
            bottomdialog.dismiss()
        }
        btnNo.setOnClickListener {
            bottomdialog.dismiss()
        }
        // below line is use to set cancelable to avoid
        // closing of dialog box when clicking on the screen.
        bottomdialog.setCancelable(true)

        // on below line we are setting
        // content view to our view.
        bottomdialog.setContentView(view)

        // on below line we are calling
        // a show method to display a dialog.
        bottomdialog.show()
    }
    fun dashboardbottomsheet() {
        // on below line we are creating a new bottom sheet dialog.
        bottomdialog = BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme)
        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.fill_bank_bottomsheet, null)
        val metrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(metrics)
        bottomdialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomdialog.behavior.peekHeight = metrics.heightPixels
        var btnOtpSubmit = view.findViewById<TextView>(R.id.btnOtpSubmit)

        btnOtpSubmit.setOnClickListener {
            startActivity(Intent(this, FillDetailsActivity::class.java).putExtra(
                "token",userPref.getToken().toString()
            ))
            bottomdialog.dismiss()
        }
        // below line is use to set cancelable to avoid
        // closing of dialog box when clicking on the screen.
        bottomdialog.setCancelable(false)

        // on below line we are setting
        // content view to our view.
        bottomdialog.setContentView(view)

        // on below line we are calling
        // a show method to display a dialog.
        bottomdialog.show()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.flContent, HomeFragment())
                    .commit()
                return true
            }
            R.id.navigation_chat -> {
                supportFragmentManager.beginTransaction().replace(R.id.flContent, ChatFragment())
                    .commit()
                return true
            }
            R.id.navigation_call -> {
                supportFragmentManager.beginTransaction().replace(R.id.flContent, CallFragment())
                    .commit()
                return true
            }
//            R.id.navigation_golive -> {
//                supportFragmentManager.beginTransaction().replace(R.id.flContent, LiveWithTopicFragment())
//                    .commit()
//                return true
//            }
            R.id.navigation_notification -> {
                supportFragmentManager.beginTransaction().replace(R.id.flContent, NotificationFragment())
                    .commit()
                return true
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        if (userPref.get_is_new() == "1"){
            dashboardbottomsheet()
            // toast("1")
        }
        Glide.with(this).load(userPref.getProfileImage())
            .apply(RequestOptions.placeholderOf(R.drawable.user_image_place_holder))
            .apply(RequestOptions.errorOf(R.drawable.user_image_place_holder))
            .into(binding.drawerToolbar.ivHeaderpic)

    }

}