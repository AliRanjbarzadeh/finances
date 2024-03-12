package ir.aliranjbarzadeh.finances.base.di

import ir.aliranjbarzadeh.finances.base.di.ModifyHeadersInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class BaseHttpClient @Inject constructor(
    cache: Cache,
    modifyHeadersInterceptor: ModifyHeadersInterceptor
) {
    val okHttpClient = OkHttpClient()
        .newBuilder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .addNetworkInterceptor(modifyHeadersInterceptor)
        .build()
}