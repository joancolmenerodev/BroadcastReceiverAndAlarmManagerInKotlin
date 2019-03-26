package com.joancolmenero.broadcastreceiverandalarmmanagerkotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context,"This toast will be shown every X minutes", Toast.LENGTH_SHORT).show()
    }
}