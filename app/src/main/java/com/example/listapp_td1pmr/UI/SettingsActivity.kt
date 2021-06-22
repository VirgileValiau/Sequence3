package com.example.listapp_td1pmr.UI

import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.Preference
import android.preference.Preference.OnPreferenceChangeListener
import android.preference.PreferenceActivity
import com.example.listapp_td1pmr.R

class SettingsActivity : PreferenceActivity(),OnPreferenceChangeListener {
    var edtpl: EditTextPreference? = null
    var BaseUrl: EditTextPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
        edtpl = findPreference("login") as EditTextPreference
        BaseUrl = findPreference("urlDeBase") as EditTextPreference
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        if (newValue == false) {
            edtpl!!.text = ""
        }
        return true
    }
}