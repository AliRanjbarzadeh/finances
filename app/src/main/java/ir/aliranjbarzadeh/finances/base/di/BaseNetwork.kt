package ir.aliranjbarzadeh.finances.base.di

import android.content.Context
import ir.aliranjbarzadeh.finances.base.Configs
import ir.aliranjbarzadeh.finances.base.helpers.PackageHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseNetwork @Inject constructor(context: Context) {
	val baseUrl: String = if (PackageHelper.isDebuggable(context)) {
		Configs.Defaults.BASE_URL_DEV
	} else {
		Configs.Defaults.BASE_URL
	}
}