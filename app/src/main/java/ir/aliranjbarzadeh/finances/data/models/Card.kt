package ir.aliranjbarzadeh.finances.data.models

import android.os.Parcelable
import ir.aliranjbarzadeh.finances.base.helpers.DateTimeHelper
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Card(
	val id: Long = 0,
	var bankId: Long,
	var name: String,
	var balance: Long = 0,
	var isDefault: Boolean,
	var createdAt: Date,
	var updatedAt: Date,
) : Parcelable {
	companion object {
		fun emptyCard(isDefault: Boolean = false): Card {
			val currentDate = DateTimeHelper.currentDateUTC()
			return Card(
				bankId = 0, name = "", isDefault = isDefault, createdAt = currentDate, updatedAt = currentDate
			)
		}
	}
}