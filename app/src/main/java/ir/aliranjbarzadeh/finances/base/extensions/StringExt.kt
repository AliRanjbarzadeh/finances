package ir.aliranjbarzadeh.finances.base.extensions

fun String.fixArabic(): String {
	return this.replace("ي", "ی")
		.replace("ك", "ک")
		.replace("دِ", "د")
		.replace("بِ", "ب")
		.replace("زِ", "ز")
		.replace("زِ", "ز")
		.replace("ِشِ", "ش")
		.replace("ِسِ", "س")
		.replace("ى", "ی")
}

fun String.toEnglish(): String {
	val chars = CharArray(length)
	for (i in indices) {
		var ch: Char = get(i)
		if (ch.code in 0x0660..0x0669) {
			ch -= 0x0660.toChar() - '0'.code.toChar()
		} else if (ch.code in 0x06f0..0x06F9) {
			ch -= 0x06f0.toChar() - '0'.code.toChar()
		}
		chars[i] = ch
	}
	return String(chars)
}

fun String.toPersian(): String {
	val persianDigits = charArrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
	val chars = CharArray(length)
	for (i in indices) {
		val digit: Char = get(i)
		if (Character.isDigit(digit)) {
			val digitValue = Character.getNumericValue(digit)
			chars[i] = persianDigits[digitValue]
		} else {
			chars[i] = digit
		}
	}

	return String(chars)
}

fun String.toFlagEmoji(): String {
	// 1. It first checks if the string consists of only 2 characters: ISO 3166-1 alpha-2 two-letter country codes (https://en.wikipedia.org/wiki/Regional_Indicator_Symbol).
	if (this.length != 2) {
		return this
	}

	val countryCodeCaps = this.uppercase() // upper case is important because we are calculating offset
	val firstLetter = Character.codePointAt(countryCodeCaps, 0) - 0x41 + 0x1F1E6
	val secondLetter = Character.codePointAt(countryCodeCaps, 1) - 0x41 + 0x1F1E6

	// 2. It then checks if both characters are alphabet
	if (!countryCodeCaps[0].isLetter() || !countryCodeCaps[1].isLetter()) {
		return this
	}
	return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}