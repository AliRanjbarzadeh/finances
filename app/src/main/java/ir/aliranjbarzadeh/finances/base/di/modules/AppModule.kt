package ir.aliranjbarzadeh.finances.base.di.modules

import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.aliranjbarzadeh.finances.base.Configs
import ir.aliranjbarzadeh.finances.base.di.BaseHttpClient
import ir.aliranjbarzadeh.finances.base.di.BaseNetwork
import ir.aliranjbarzadeh.finances.base.di.BaseRetrofit
import ir.aliranjbarzadeh.finances.base.di.Logger
import ir.aliranjbarzadeh.finances.base.dispatchers.DispatchersProvider
import ir.aliranjbarzadeh.finances.base.dispatchers.DispatchersProviderImpl
import ir.aliranjbarzadeh.finances.base.exceptions.NetworkExceptionHandler
import ir.aliranjbarzadeh.finances.base.helpers.PackageHelper
import ir.aliranjbarzadeh.finances.data.sources.local.Database
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun providesContentResolver(@ApplicationContext appContext: Context): ContentResolver = appContext.contentResolver

	@Provides
	@Singleton
	fun providesGson(): Gson = GsonBuilder().create()

	@Provides
	@Singleton
	fun providesOkHttpClient(baseHttpClient: BaseHttpClient): OkHttpClient = baseHttpClient.okHttpClient

	@Provides
	@Singleton
	fun providesRetrofit(baseRetrofit: BaseRetrofit): Retrofit = baseRetrofit.retrofit

	@Provides
	@Singleton
	fun providesDispatcher(dispatcherProvider: DispatchersProviderImpl): DispatchersProvider = dispatcherProvider.dispatcher

	@Provides
	@Singleton
	fun providesApiExceptionHandler(gson: Gson): NetworkExceptionHandler = NetworkExceptionHandler(gson)

	@Provides
	@Singleton
	fun providesCache(@ApplicationContext appContext: Context): Cache = Cache(appContext.cacheDir, 30 * 1024 * 1024)

	@Provides
	@Singleton
	fun providesBaseUrl(@ApplicationContext appContext: Context): BaseNetwork = BaseNetwork(appContext)

	@Provides
	@Singleton
	fun providesLogger(@ApplicationContext appContext: Context): Logger = Logger(appContext)

	@Provides
	@Singleton
	fun providesDatabase(@ApplicationContext appContext: Context): Database = Room.databaseBuilder(
		appContext, Database::class.java, Configs.database
	).apply {
		if (PackageHelper.isDebuggable(appContext)) {
			fallbackToDestructiveMigration()
		}
	}.build()

}