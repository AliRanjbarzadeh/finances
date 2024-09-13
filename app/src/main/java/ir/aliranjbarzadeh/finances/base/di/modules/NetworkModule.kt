package ir.aliranjbarzadeh.finances.base.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.aliranjbarzadeh.finances.base.dispatchers.DispatchersProvider
import ir.aliranjbarzadeh.finances.base.util.NetworkWatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	@Provides
	@Singleton
	fun providesNetworkWatcher(@ApplicationContext context: Context, dispatchersProvider: DispatchersProvider): NetworkWatcher = NetworkWatcher(context, dispatchersProvider)
}