package com.example.youtubevideogmbn.dagger

import com.example.youtubevideogmbn.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules= [RepoModule::class, WebServicesModule::class])

interface VideoComponent {
    fun inject(mainActivity: MainActivity)
}
