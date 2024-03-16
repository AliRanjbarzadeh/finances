package ir.aliranjbarzadeh.finances.base.helpers

import java.text.NumberFormat

object NumberHelper {
	fun format(number: Long, sign: String = ""): String {
		if (sign.isNotEmpty()) {
			return getNumberFormatter().format(number) + " $sign"
		}
		return getNumberFormatter().format(number)
	}

	fun getNumberFormatter(): NumberFormat {
		return NumberFormat.getInstance()
	}
}