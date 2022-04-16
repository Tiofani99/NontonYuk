package com.tiooooo.nontonyuk.service

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.tiooooo.core.data.remote.datasource.movie.MovieRemoteDataSource
import com.tiooooo.core.domain.model.Movie
import com.tiooooo.core.domain.model.general.ArgumentWrapper
import com.tiooooo.core.utils.IntentExtra
import com.tiooooo.core.utils.Routes
import com.tiooooo.core.utils.extensions.putJSON
import com.tiooooo.nontonyuk.R
import com.tiooooo.nontonyuk.ui.base.DetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber
import java.util.*

class MovieReminder : BroadcastReceiver(), KoinComponent {

    companion object {
        const val CHANNEL_ID = "notification_app_reminder"
        private const val CHANNEL_NAME = "Reminder Notification"
        const val ID_DAILY_REMINDER = 100
        const val EXTRA_MOVIE = "extra_movie"
    }

    private val scope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO) }

    private val context: Context by inject(Context::class.java)

    override fun onReceive(context: Context, intent: Intent) {
        intent.extras?.getParcelable<Movie>(EXTRA_MOVIE)?.let {
            sendPushNotification(context, it)
        }
    }

    fun setNowPlayingMovie(movie: Movie) {
        val intent = Intent(context, MovieReminder::class.java).apply {
            putExtra(EXTRA_MOVIE, movie)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_DAILY_REMINDER,
            intent,
            0
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            getReminderTime().timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun getReminderTime(): Calendar {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 10
        calendar[Calendar.MINUTE] = 30
        calendar[Calendar.SECOND] = 0
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }
        return calendar
    }

    @SuppressLint("LaunchActivityFromNotification")
    private fun sendPushNotification(context: Context, movie: Movie) {
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(IntentExtra.ROUTE, Routes.DETAIL_MOVIE.name)
            putJSON(IntentExtra.ARGS, ArgumentWrapper(id = movie.id.toString()))
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            ID_DAILY_REMINDER,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Check this ${movie.title}")
            .setContentText(movie.overview)
            .setSmallIcon(R.drawable.ic_logo)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            with(channel) {
                setShowBadge(true)
            }
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }
        val notification = mBuilder.build()
        notification.flags = NotificationCompat.FLAG_INSISTENT
        mNotificationManager.notify(ID_DAILY_REMINDER, notification)
    }
}