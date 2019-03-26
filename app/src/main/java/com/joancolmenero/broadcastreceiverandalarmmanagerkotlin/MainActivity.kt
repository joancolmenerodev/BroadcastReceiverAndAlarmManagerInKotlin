package com.joancolmenero.broadcastreceiverandalarmmanagerkotlin

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private var mAlarmManager : AlarmManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        btn_start_alarm.setOnClickListener { view ->
            val mIntent = Intent(this, MyReceiver::class.java)

            val mPendingIntent = PendingIntent.getBroadcast(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            mAlarmManager = this
                .getSystemService(Context.ALARM_SERVICE) as AlarmManager
            mAlarmManager!!.setRepeating(
                AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                1000, mPendingIntent
            )
        }

        btn_cancel_alarm.setOnClickListener {
            val mIntent = Intent(this, MyReceiver::class.java)
            val mPendingIntent = PendingIntent.getBroadcast(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            mAlarmManager!!.cancel(mPendingIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareBackupData(file: File) {

        val jsonUri = FileProvider.getUriForFile(
            this,
            this.applicationContext.packageName + ".com.joancolmenero.sharejsonfileviaintent.provider",
            file
        )

        Toast.makeText(this,"this is to check if exists ${file.exists()}",Toast.LENGTH_LONG).show()
        if (file.exists()) {
            val intentShareFile = Intent(Intent.ACTION_SEND)
            intentShareFile.putExtra(Intent.EXTRA_STREAM, jsonUri)

            intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(Intent.createChooser(intentShareFile, "Share File"))
        }


    }
}
