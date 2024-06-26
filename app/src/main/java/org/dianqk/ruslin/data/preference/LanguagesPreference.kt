package org.dianqk.ruslin.data.preference

import android.content.Context
import android.os.LocaleList
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.dianqk.ruslin.R
import org.dianqk.ruslin.data.DataStoreKeys
import org.dianqk.ruslin.data.dataStore
import java.util.Locale

// See https://github.com/Ashinch/ReadYou/blob/435a6ea57704f45871565cb8980e1e45b69ff884/app/src/main/java/me/ash/reader/data/model/preference/LanguagesPreference.kt#L17.

sealed class LanguagesPreference(val value: Int) {
    object UseDeviceLanguages : LanguagesPreference(0)
    object English : LanguagesPreference(1)
    object ChineseSimplified : LanguagesPreference(2)
    object Farsi : LanguagesPreference(3)
    object Russian : LanguagesPreference(4)

    fun put(context: Context, scope: CoroutineScope) {
        scope.launch {
            context.dataStore.edit {
                it[DataStoreKeys.Languages.key] = value
            }
            setLocale(context)
        }
    }

    fun getDesc(context: Context): String =
        when (this) {
            UseDeviceLanguages -> context.getString(R.string.use_device_languages)
            English -> context.getString(R.string.english)
            ChineseSimplified -> context.getString(R.string.chinese_simplified)
            Farsi -> context.getString(R.string.farsi)
            Russian -> context.getString(R.string.russian)
        }

    fun getLocale(): Locale =
        when (this) {
            UseDeviceLanguages -> LocaleList.getDefault().get(0)
            English -> Locale("en", "US")
            ChineseSimplified -> Locale("zh", "CN")
            Farsi -> Locale("fa", "IR")
            Russian -> Locale("ru", "RU")
        }

    fun setLocale(context: Context) {
        val locale = getLocale()
        val resources = context.resources
        val metrics = resources.displayMetrics
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLocales(LocaleList(locale))
        context.createConfigurationContext(configuration)
        resources.updateConfiguration(configuration, metrics)

        val appResources = context.applicationContext.resources
        val appMetrics = appResources.displayMetrics
        val appConfiguration = appResources.configuration
        appConfiguration.setLocale(locale)
        appConfiguration.setLocales(LocaleList(locale))
        context.applicationContext.createConfigurationContext(appConfiguration)
        appResources.updateConfiguration(appConfiguration, appMetrics)
    }

    companion object {
        val default = UseDeviceLanguages
        val values = listOf(
            UseDeviceLanguages,
            English,
            ChineseSimplified,
            Farsi,
            Russian
        )

        fun fromPreferences(preferences: Preferences): LanguagesPreference =
            when (preferences[DataStoreKeys.Languages.key]) {
                0 -> UseDeviceLanguages
                1 -> English
                2 -> ChineseSimplified
                3 -> Farsi
                4 -> Russian
                else -> default
            }

        fun fromValue(value: Int): LanguagesPreference =
            when (value) {
                0 -> UseDeviceLanguages
                1 -> English
                2 -> ChineseSimplified
                3 -> Farsi
                4 -> Russian
                else -> default
            }
    }
}
