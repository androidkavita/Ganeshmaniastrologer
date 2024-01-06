package com.callastro.broadcastReceiver

//import com.example.swastharakshak.chats.VoiceCallActivity

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Vibrator
import com.callastro.R
import com.callastro.fcm.MyFirebaseMessagingService
import com.maxtra.u.Constant


class NotificationBroadcastReceiver : BroadcastReceiver() {
//    private var incomingCallNotificationBuilder: FirebaseMessaging? = null
//    lateinit var apppcontext:Context
    private var vib: Vibrator? = null
    var mMediaPlayer: MediaPlayer? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (intent != null) {
            val eventType = intent.getStringExtra("action")
            val roomId = intent.getStringExtra("Channelname")
            val type = intent.getStringExtra("types")
            val notId = intent.getIntExtra(Constant.NOTIFICATION_ID, 0)
            val token = intent.getStringExtra(Constant.TOKEN)
            val doctor_name = intent.getStringExtra("doctor_name")
            val doctor_image = intent.getStringExtra("doctor_image")

//            println("shivani>" + eventType)
//            apppcontext = this

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(notId!!)

            var vib: Vibrator? = null
            var mMediaPlayer: MediaPlayer? = null
            mMediaPlayer = MediaPlayer.create(context, R.raw.notification)
            vib = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vib!!.vibrate(1000)
            mMediaPlayer!!.isLooping = false
            mMediaPlayer!!.start()


//            mMediaPlayer = MediaPlayer.create(context, R.raw.notification)
//            vib = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//            vib!!.vibrate(1000)
//            mMediaPlayer!!.isLooping = false
//            mMediaPlayer!!.start()

//            incomingCallNotificationBuilder = apppcontext


            val intent = Intent(context, MyFirebaseMessagingService::class.java)
//            Toast.makeText(context, "hiiftrhtrhtrhtrff", Toast.LENGTH_SHORT).show()

            intent.putExtra("id", 101)
            intent.putExtra("msg", "hi")
            context.sendBroadcast(intent)

//            FirebaseMessaging().stopSound(context)
            //starting service
//            stopSound()
            //starting service


//            Toast.makeText(context, "hiiftrhtrhtrhtrff", Toast.LENGTH_SHORT).show()
            
//            val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
//            val wl = pm.newWakeLock(
//                PowerManager.PARTIAL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
//                "myalarmapp:alarm."
//            )
//            wl.acquire(5000)
            val notifManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notifManager.cancelAll()
//            eventType?.let {
////                if (it.equals("Accept")) {
////                    Toast.makeText(context, eventType, Toast.LENGTH_SHORT).show()
//                    if (type == "1"){
//
//                        val intent = Intent(context, VoiceCallActivity::class.java).apply {
//                            putExtra("channelName", roomId)
//                            putExtra("token", token)
//                            putExtra("doctor_name", doctor_name)
//                            putExtra("doctor_image", doctor_image)
//                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        }
//                        context.startActivity(intent)
////                        context.sendBroadcast(intent)
//                        NotificationManagerCompat.from(context).cancel(null, 321);
//                    }else if (type == "2"){
////                        Toast.makeText(context, "hiihtrhtrhtrfff", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(context, VideoCallActivity::class.java).apply {
//                            putExtra("channelName", roomId)
//                            putExtra("token", token)
//                            putExtra("doctor_name", doctor_name)
//                            putExtra("doctor_image", doctor_image)
//                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//                        }
//                        context.startActivity(intent)
//
////                        context.sendBroadcast(intent)
//                        NotificationManagerCompat.from(context).cancel(null, 321);
//                    }
////                    Toast.makeText(context, "hiiftrhtrhtrhtrff", Toast.LENGTH_SHORT).show()
////                }else if (it.equals("Reject")) {
////                    context.stopService(Intent(context, FirebaseMessaging::class.java))
////                    val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
////                    context.sendBroadcast(it)
////
////                }
//            }
        }

    }
    @SuppressLint("MissingPermission")
    fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            vib!!.cancel()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }


}