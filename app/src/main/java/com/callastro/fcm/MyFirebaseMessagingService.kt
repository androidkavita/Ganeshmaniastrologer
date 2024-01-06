package com.callastro.fcm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import com.callastro.R
import com.callastro.broadcastReceiver.NotificationBroadcastReceiver
import com.callastro.ui.activities.CallRequest
import com.callastro.ui.activities.ChatwithUsActivity
import com.callastro.util.SoundUtil
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.maxtra.u.Constant


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var count = 0
    var notificationCount = 0
    var mNotificationId = 1
    private var vib: Vibrator? = null
    var mMediaPlayer: MediaPlayer? = null

    override fun onMessageReceived(remote: RemoteMessage) {
        super.onMessageReceived(remote)
        Log.d("TAG", "onMessageReceived: " + remote.data)
        val data = remote.data
        val title = remote.notification?.title ?: "title"
        val body = remote.notification?.body ?: "body"
        createNotification(body, remote.data, title)
        Log.d("title1", "title1: " + title)
        Log.d("body1", "body1: " + body)

        val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // internet lost alert dialog method call from here...
//                broadCastingReceiver.ma!!.chatList()
            }
        }
    }

    @SuppressLint("LaunchActivityFromNotification")
    private fun createNotification(
        body1: String,
        data: MutableMap<String, String>, title1: String
    ) {
        try {

            val notificationType: String = data["call_type"].toString()
            val type: String = data["type"].toString()
            val user_id: String = data["astro_id"].toString()
            val user_name: String = data["astro_name"].toString()
            val agora_token: String = data["agora_token"].toString()
            val channel_name: String = data["channel_name"].toString()
            val appId: String = data["app_id"].toString()
            val title = data["body"].toString()
            val name: String = data["title"].toString()
            val other_user: String = data["other_user"].toString()

            SoundUtil.playSound(this)

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val notificationBuilder =
                NotificationCompat.Builder(
                    this,
                    getString(R.string.app_name)
                )
                    .setSmallIcon(getNotificationIcon())
                  /*  .setSound(defaultSoundUri)*/
                    .setContentTitle(title1)
                    .setContentText(body1)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setChannelId(getString(R.string.app_name))
            val notificationBuilder1 =
                NotificationCompat.Builder(
                    this,
                    getString(R.string.app_name)
                )
                    .setSmallIcon(getNotificationIcon())
                    /*.setSound(defaultSoundUri)*/
                    .setContentTitle(title1)
                    .setContentText(body1)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setChannelId(getString(R.string.app_name))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationBuilder.setChannelId(getString(R.string.app_name))
                val notificationChannel = NotificationChannel(
                    getString(R.string.app_name),
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                notificationManager.createNotificationChannel(notificationChannel)


                val contentIntent = PendingIntent.getBroadcast(
                    this,
                    0,
                    //  Intent(this, ChatwithUsActivity::class.java)
                    Intent(this, NotificationBroadcastReceiver::class.java)
                        .putExtra("other_user", other_user)
                        .putExtra("title", name)
                        .putExtra("call_type", notificationType)
                        .putExtra("list_id", user_id)
                        .putExtra("list_userName", user_name)
                        .putExtra("app_id", appId)
                        .putExtra("channel_name", channel_name)
                        .putExtra(Constant.NOTIFICATION_ID, 321)
                        .putExtra("agora_token", agora_token)
                        .putExtra("type", type),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
//                     notificationBuilder.clearActions()
                notificationBuilder.setContentIntent(contentIntent)


                if (notificationType == "3") {
                    SoundUtil.playSound(this)
                    try {
                        val contentIntent = PendingIntent.getActivity(
                            this,
                           1,
                          //  Intent(this, ChatwithUsActivity::class.java)
                            Intent(this, ChatwithUsActivity::class.java)
                                .putExtra("other_user", other_user)
                                .putExtra("title", name)
                                .putExtra("call_type", notificationType)
                                .putExtra("list_id", user_id)
                                .putExtra("list_userName", user_name)
                                .putExtra("app_id", appId)
                                .putExtra("channel_name", channel_name)
                                .putExtra("agora_token", agora_token),
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
//                     notificationBuilder.clearActions()
                        notificationBuilder.setContentIntent(contentIntent)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else if (notificationType == "2") {
                    SoundUtil.playSound(this)
                    try {
                        val contentIntent = PendingIntent.getActivity(
                            this,
                            2,
                           // Intent(this, IncomingCallActivity::class.java).putExtra(
                            Intent(this, CallRequest::class.java).putExtra(
                                "other_user",
                                other_user
                            )
                                .putExtra("title", name)
                                .putExtra("app_id", appId)
                                .putExtra("channel_name", channel_name)
                                .putExtra("call_type", "2") //Video Call Request
                                .putExtra("list_id", user_id)
                                .putExtra("astro_name", user_name)
                                .putExtra("agora_token", agora_token),
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                        notificationBuilder.setContentIntent(contentIntent)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else if (notificationType == "1") {
                    SoundUtil.playSound(this)
                    try {
                        val contentIntent = PendingIntent.getActivity(
                            this,
                          3,
                         //   Intent(this, DashboardAudioCallActivity::class.java).putExtra(
                            Intent(this, CallRequest::class.java).putExtra(
                                "other_user",
                                other_user
                            )
                                .putExtra("title", name)
                                .putExtra("app_id", appId)
                                .putExtra("channel_name", channel_name)
                                .putExtra("call_type", "1")  //Audio Call Request
                                .putExtra("list_id", user_id)
                                .putExtra("astro_name", user_name)
                                .putExtra("notification","1")
                                .putExtra("agora_token", agora_token),
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                        notificationBuilder.setContentIntent(contentIntent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }

            }

// Gets an instance of the NotificationManager service


// Gets an instance of the NotificationManager service
            val mNotificationManager =
                this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
// Builds the notification and issues it.


// Builds the notification and issues it.
            mNotificationManager.notify(mNotificationId, notificationBuilder.build())

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
/*    fun playSound() {
//            if (mMediaPlayer == null) {
        mMediaPlayer = MediaPlayer.create(this, R.raw.rington)
        vib = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vib!!.vibrate(1000)
        mMediaPlayer!!.isLooping = false
        mMediaPlayer!!.start()
//            } else mMediaPlayer!!.start()

    }*/
    private fun getNotificationIcon(): Int {

        val useWhiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        return if (useWhiteIcon) R.drawable.logo else R.drawable.logo
    }

}