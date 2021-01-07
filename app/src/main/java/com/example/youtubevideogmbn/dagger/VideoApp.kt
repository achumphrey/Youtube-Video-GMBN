package com.example.youtubevideogmbn.dagger

import android.app.Application

class VideoApp : Application(){

    companion object {
        private lateinit var component: VideoComponent
        fun getComponent() = component
    }

    override fun onCreate() {
        super.onCreate()
        component = buildComponent()
    }

    private fun buildComponent(): VideoComponent {
       return DaggerVideoComponent.builder()
            .repoModule(RepoModule())
            .webServicesModule(WebServicesModule())
            .build()
    }
}