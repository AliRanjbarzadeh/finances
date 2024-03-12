package ir.aliranjbarzadeh.finances.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Transaction(
	val id: Long = 0,
	var bankId: Long,
	var cardId: Long,
	var categoryId: Long,
	var contactId: Long? = null,
	var price: Long,
	var type: String,
	var description: String,
	var createdAt: Date,
	var updatedAt: Date,
) : Parcelable {
	override fun toString(): String {
		return "Transaction(id=$id, bankId=$bankId, cardId=$cardId, categoryId=$categoryId, contactId=$contactId, price=$price, type='$type', description='$description', createdAt=$createdAt, updatedAt=$updatedAt)"
	}
}