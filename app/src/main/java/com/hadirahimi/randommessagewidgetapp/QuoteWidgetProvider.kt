package com.hadirahimi.randommessagewidgetapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.util.Random

class QuoteWidgetProvider : AppWidgetProvider()
{
    override fun onUpdate(
        context : Context ,
        appWidgetManager : AppWidgetManager ,
        appWidgetIds : IntArray
    )
    {
        val views = RemoteViews(context.packageName,R.layout.activity_main)
        val intent = Intent(context,QuoteWidgetProvider::class.java)
        intent.action = REFRESH_QUOTE_ACTION
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.refreshButton,pendingIntent)
        val randomQuote = getRandomQuote(context)
        views.setTextViewText(R.id.quoteTextView,randomQuote)
        appWidgetManager.updateAppWidget(appWidgetIds,views)
    }
    private fun updateWidgetQuote(context : Context,quote:String)
    {
        val views = RemoteViews(context.packageName,R.layout.activity_main)
        views.setTextViewText(R.id.quoteTextView,quote)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val widgetComponent = ComponentName(context,QuoteWidgetProvider::class.java)
        appWidgetManager.updateAppWidget(widgetComponent,views
        )
    }
    
    override fun onReceive(context : Context , intent : Intent)
    {
        super.onReceive(context , intent)
        if (REFRESH_QUOTE_ACTION == intent.action)
        {
            val randomQuote = getRandomQuote(context)
            updateWidgetQuote(context,randomQuote)
        }
    }
    private fun getRandomQuote(context : Context) : String
    {
        val quotes = context.resources.getStringArray(R.array.inspirational_quotes)
        return quotes[Random().nextInt(quotes.size)]
    }
    companion object{
        private const val REFRESH_QUOTE_ACTION = "com.hadirahimi.randommessagewidgetapp.ACTION_REFRESH_QUOTE"
    }
}















