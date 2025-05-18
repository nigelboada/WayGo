package com.example.waygo.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.waygo.data.AppInfo
import javax.inject.Inject

@HiltViewModel
class VersionViewModel @Inject constructor() : ViewModel() {
    val versionName: String = AppInfo.VERSION_NAME
    val versionCode: Int = AppInfo.VERSION_CODE
}