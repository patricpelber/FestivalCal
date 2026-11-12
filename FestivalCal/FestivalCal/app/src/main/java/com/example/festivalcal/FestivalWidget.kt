package com.example.festivalcal

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class FestivalWidget : AppWidgetProvider() {

    override fun onUpdate(ctx: Context, mgr: AppWidgetManager, ids: IntArray) {
        ids.forEach { mgr.updateAppWidget(it, build(ctx)) }
    }

    override fun onReceive(ctx: Context, intent: Intent) {
        super.onReceive(ctx, intent)
        val a = intent.action
        if (a == Intent.ACTION_DATE_CHANGED || a == Intent.ACTION_TIME_SET || a == Intent.ACTION_TIMEZONE_CHANGED) {
            val mgr = AppWidgetManager.getInstance(ctx)
            onUpdate(ctx, mgr, mgr.getAppWidgetIds(ComponentName(ctx, FestivalWidget::class.java)))
        }
    }

    private fun build(ctx: Context): RemoteViews {
        val v = RemoteViews(ctx.packageName, R.layout.widget)
        Festivals.init(ctx)
        v.setTextViewText(R.id.fest, Festivals.todayText())
        val pi = PendingIntent.getActivity(
            ctx, 0, Intent(ctx, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        v.setOnClickPendingIntent(R.id.root, pi)
        return v
    }
}
