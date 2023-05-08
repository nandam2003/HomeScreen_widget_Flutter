package com.example.widget_app

import android.content.SharedPreferences
import android.widget.Toast
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.widget.RemoteViews
import android.content.ComponentName


class MainActivity: FlutterActivity() {

    private val channelName = "example/sampleWidget"


    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        var channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger,channelName)
        channel.setMethodCallHandler { call, result ->

            if(call.method == "showToast"){
                val intent = Intent(this, AppWidget::class.java)
                intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                val ids = AppWidgetManager.getInstance(this).getAppWidgetIds(ComponentName(this, AppWidget::class.java))
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
                sendBroadcast(intent)
                }

            }
        }
    }
