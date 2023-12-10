package com.snick55.smartlist.core

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application()


fun log(message: String) = Log.d("TAG",message)