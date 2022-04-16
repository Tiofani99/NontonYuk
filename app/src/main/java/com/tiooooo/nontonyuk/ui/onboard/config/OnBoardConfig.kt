package com.tiooooo.nontonyuk.ui.onboard.config

import android.content.Context
import android.content.SharedPreferences

class OnBoardConfig(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    private var prefsEditor: SharedPreferences.Editor = prefs.edit()

    fun setFirstTimeLaunch(isFirstTme: Boolean) {
        prefsEditor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTme)
        prefsEditor.commit()
    }

    fun isFirstTimeLaunch(): Boolean = prefs.getBoolean(IS_FIRST_TIME_LAUNCH, true)

    companion object {
        private const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
    }
}