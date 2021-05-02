package co.ghostnotes.sample.arcprogress

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class ArcProgressApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())
    }
}