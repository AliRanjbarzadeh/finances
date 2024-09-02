package ir.aliranjbarzadeh.finances.base.extensions

import java.text.NumberFormat
import java.util.Locale

fun Int.priceFormat(suffix: String = "تومان"): String {
	val format = NumberFormat.getNumberInstance(Locale.US).format(this)
	return if (suffix.isNotEmpty()) {
		("$format $suffix ")
	} else {
		format
	}
}

fun Long.priceFormat(suffix: String = ""): String {
	val format = NumberFormat.getNumberInstance(Locale.US).format(this)
	return if (suffix.isNotEmpty()) {
		("$format $suffix ")
	} else {
		format
	}
}