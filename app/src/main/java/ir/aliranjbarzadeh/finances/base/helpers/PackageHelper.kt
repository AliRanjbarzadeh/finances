package ir.aliranjbarzadeh.finances.base.helpers

import android.content.Context
import android.content.pm.ApplicationInfo


object PackageHelper {
	fun isDebuggable(context: Context): Boolean {
		return context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
	}
}