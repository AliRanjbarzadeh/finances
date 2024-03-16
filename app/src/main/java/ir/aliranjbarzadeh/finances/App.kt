package ir.aliranjbarzadeh.finances

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp
import ir.aliranjbarzadeh.finances.base.helpers.FontHelper
import ir.aliranjbarzadeh.finances.base.helpers.LanguageHelper
import ir.aliranjbarzadeh.finances.base.helpers.LocaleHelper

@HiltAndroidApp
class App : MultiDexApplication() {

	override fun onCreate() {
		super.onCreate()
		initHawk()
		initLanguage()
		initFont()

		//Disable dark mode
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
	}

	private fun initHawk() {
		Hawk.init(this).build()
	}

	private fun initLanguage() {
		LocaleHelper.setLocale(this, LanguageHelper.getLanguage())
	}

	private fun initFont() {
		FontHelper.setFont(this)
	}
}