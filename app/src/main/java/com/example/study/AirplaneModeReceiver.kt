package com.example.study

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AirplaneModeReceiver : BroadcastReceiver (){
    companion object {
        private const val TAG = "AirplaneModeReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {

            val isOn = intent.getBooleanExtra("state", false)

            if (isOn) {
                Log.d(TAG, "✈️ Airplane Mode ON")
            } else {
                Log.d(TAG, "📶 Airplane Mode OFF")
            }
        }
    }
}