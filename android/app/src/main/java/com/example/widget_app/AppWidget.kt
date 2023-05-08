package com.example.widget_app

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import io.flutter.embedding.android.FlutterActivity
import android.content.Intent
import android.app.PendingIntent


/**
 * Implementation of App Widget functionality.
 */
class AppWidget : AppWidgetProvider() {

    companion object {
        const val ACTION_OPEN_APP = "com.example.widget_app.OPEN_APP"
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }


//    override fun onAppWidgetOptionsChanged(
//        context: Context,
//        appWidgetManager: AppWidgetManager,
//        appWidgetId: Int,
//        newOptions: Bundle
//    ) {
////        var minWidth:Int = 0
////        var maxHeight:Int = 0
////        var maxWidth:Int = 0
////        var minHeight:Int = 0
//        val views = RemoteViews(context.packageName, R.layout.app_widget)
//        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
//           var minWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)
//           var maxHeight= newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT)
//            var maxWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH)
//            var minHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT)
//        if(minWidth > 140){
//            views.setViewVisibility(R.id.ly,View.VISIBLE)
//            views.setViewVisibility(R.id.appwidget_text,View.GONE)
//        }else{
//            views.setViewVisibility(R.id.ly,View.GONE)
//            views.setViewVisibility(R.id.appwidget_text,View.VISIBLE)
//        }
//        appWidgetManager.updateAppWidget(appWidgetId, views)
//
//    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        // Handle the open app action
        if (intent.action == ACTION_OPEN_APP) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {

    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)



    val mPrefs: SharedPreferences = context.getSharedPreferences("FlutterSharedPreferences",
        FlutterActivity.MODE_PRIVATE
    )
    val count = mPrefs.getLong("flutter.count", 0)
    val widgetText = "$count"
    // Construct the RemoteViews object
    // Set up the views for the widget
    val views = RemoteViews(context.packageName, R.layout.app_widget)
    views.setOnClickPendingIntent(R.id.my_widget_layout, pendingIntent)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}