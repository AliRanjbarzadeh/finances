package ir.aliranjbarzadeh.finances.base.helpers

import ir.aliranjbarzadeh.finances.base.Configs
import ir.aliranjbarzadeh.finances.base.extensions.loadFromSp

object LanguageHelper {
	fun getLanguage(): String {
		return loadFromSp(Configs.Sessions.language, Configs.Defaults.language)
	}

	fun isDefault(language: String): Boolean {
		return language == Configs.Defaults.language
	}
}