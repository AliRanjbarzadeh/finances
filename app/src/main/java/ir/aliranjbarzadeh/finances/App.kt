package ir.aliranjbarzadeh.finances

import androidx.multidex.MultiDexApplication
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp
import ir.aliranjbarzadeh.finances.base.Configs
import ir.aliranjbarzadeh.finances.base.extensions.loadFromSp
import ir.aliranjbarzadeh.finances.base.helpers.FontHelper
import ir.aliranjbarzadeh.finances.base.helpers.LanguageHelper
import ir.aliranjbarzadeh.finances.base.helpers.LocalHelper

@HiltAndroidApp
class App : MultiDexApplication() {

	override fun onCreate() {
		super.onCreate()
		initHawk()
		initLanguage()
		initFont()
	}

	private fun initHawk() {
		Hawk.init(this).build()
	}

	private fun initLanguage() {
		LocalHelper.setLocale(this, LanguageHelper.getLanguage())
	}

	private fun initFont() {
		FontHelper.setFont(this)
	}
}