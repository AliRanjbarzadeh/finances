package ir.aliranjbarzadeh.finances.data.models

import android.os.Parcelable
import ir.aliranjbarzadeh.finances.base.helpers.DateTimeHelper
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Transaction(
	var id: Long = 0,
	var bankId: Long,
	var cardId: Long,
	var categoryId: Long,
	var contactId: Long? = null,
	var price: Long,
	var type: String,
	var description: String = "",
	var createdAt: Date,
	var updatedAt: Date,
	var card: Card? = null,
	var category: Category? = null,
) : Parcelable {

	companion object {
		fun emptyObject(): Transaction {
			val currentDate = DateTimeHelper.currentDateUTC()
			return Transaction(
				bankId = 0, cardId = 0, categoryId = 0, price = 0, type = "deposit", createdAt = currentDate, updatedAt = currentDate
			)
		}
	}

	val dateFormatted: String
		get() = DateTimeHelper.formatDateTime(createdAt)

	override fun toString(): String {
		return "Transaction(id=$id, cardId=$cardId, price=$price, type='$type', description='$description', createdAt=$createdAt, card=${card.toString()}, category=${category.toString()})"
	}
}