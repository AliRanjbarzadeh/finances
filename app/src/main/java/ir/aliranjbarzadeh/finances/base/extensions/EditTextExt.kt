package ir.aliranjbarzadeh.finances.base.extensions

import android.text.TextWatcher
import android.widget.EditText
import ir.aliranjbarzadeh.finances.base.util.NumberTextWatcher

inline fun EditText.numberFormat(
	crossinline afterTextChanged: (number: Long) -> Unit,
): TextWatcher {
	val textWatcher = NumberTextWatcher(this) { number: Long ->
		afterTextChanged.invoke(number)
	}

	addTextChangedListener(textWatcher)

	return textWatcher
}