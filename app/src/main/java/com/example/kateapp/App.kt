package com.example.kateapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp // Чтобы работал hilt
class App : Application()