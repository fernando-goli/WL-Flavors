package com.fgomes.wl_flavors.config.di

import com.fgomes.wl_flavors.config.Config
import com.fgomes.wl_flavors.config.ConfigImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ConfigModule {

    @Binds
    fun bindConfig(config: ConfigImpl): Config

}