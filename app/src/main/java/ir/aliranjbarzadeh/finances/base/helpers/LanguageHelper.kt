package ir.aliranjbarzadeh.finances.base.helpers

import ir.aliranjbarzadeh.finances.base.Configs
import ir.aliranjbarzadeh.finances.base.extensions.loadFromSp

object LanguageHelper {
	fun getLanguage(): String {
		return loadFromSp(Configs.Sessions.LANGUAGE, Configs.Defaults.LANGUAGE)
	}

	fun isDefault(language: String): Boolean {
		return language == Configs.Defaults.LANGUAGE
	}
}