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
	var deposit: Long = 0,
	var withdraw: Long = 0,
	var isDefault: Boolean,
	var createdAt: Date,
	var updatedAt: Date,
	var isSelected: Boolean = false,
) : Parcelable {
	companion object {
		fun emptyObject(isDefault: Boolean = false): Card {
			val currentDate = DateTimeHelper.currentDateUTC()
			return Card(
				bankId = 0, name = "", isDefault = isDefault, createdAt = currentDate, updatedAt = currentDate
			)
		}
	}

	val dateFormatted: String
		get() = DateTimeHelper.formatDateTime(date = createdAt, persianFormat = "j F Y", englishFormat = "MMM F, yyyy")

	val currentBalance: Long
		get() = balance + deposit - withdraw

	override fun toString(): String {
		return "Card(id=$id, name='$name', balance=$balance, deposit=$deposit, withdraw=$withdraw)"
	}
}