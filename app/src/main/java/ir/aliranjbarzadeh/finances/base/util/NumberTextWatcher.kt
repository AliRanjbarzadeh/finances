package ir.aliranjbarzadeh.finances.base.util

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import ir.aliranjbarzadeh.finances.base.extensions.toEnglish
import java.text.DecimalFormat

class NumberTextWatcher constructor(
	private val editText: EditText,
	private val afterTextChanged: (number: Long) -> Unit,
) : TextWatcher {
	private var df: DecimalFormat = DecimalFormat("#,###")

	init {
		df.isDecimalSeparatorAlwaysShown = false
	}

	override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
	}

	override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
	}

	override fun afterTextChanged(editable: Editable?) {
		editText.removeTextChangedListener(this)

		try {
			val balance = editable.toString()
				.replace(df.decimalFormatSymbols.groupingSeparator.toString(), "")
				.toEnglish()

			if (balance.isNotEmpty()) {
				val number = df.parse(balance)
				val inilen = editText.text.length
				val cp = editText.selectionStart

				editText.setText(df.format(number))
				afterTextChanged.invoke(balance.toLong())

				val endlen = editText.text.length
				val sel = (cp + (endlen - inilen))
				if (sel > 0 && sel <= editText.text.length) {
					editText.setSelection(sel)
				} else {
					editText.setSelection(editText.text.length - 1)
				}

			}
		} catch (e: Exception) {
			Log.e("NUMBER_FORMAT_INPUT", "afterTextChanged: ${e.message}", e)
		}

		editText.addTextChangedListener(this)
	}
}