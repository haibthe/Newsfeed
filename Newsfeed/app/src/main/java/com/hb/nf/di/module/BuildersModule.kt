package com.hb.nf.di.module

import com.hb.nf.di.module.sub.SystemModule
import com.hb.nf.di.scope.CustomScope
import com.hb.nf.ui.reputation.ReputationActivity
import com.hb.nf.ui.main.MainActivity
import com.hb.nf.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BuildersModule {

    @CustomScope
    @ContributesAndroidInjector(modules = [])
    abstract fun contributeSplashActivity(): SplashActivity

    @CustomScope
    @ContributesAndroidInjector(modules = [SystemModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @CustomScope
    @ContributesAndroidInjector(modules = [SystemModule::class])
    abstract fun contributeDetailActivity(): ReputationActivity
}