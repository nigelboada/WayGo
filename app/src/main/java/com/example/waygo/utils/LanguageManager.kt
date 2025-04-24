package com.example.waygo.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

object LanguageManager {
    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}

