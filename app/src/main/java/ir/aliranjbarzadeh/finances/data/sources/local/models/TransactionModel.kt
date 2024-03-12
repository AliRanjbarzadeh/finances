package ir.aliranjbarzadeh.finances.data.sources.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.aliranjbarzadeh.finances.data.base.ResponseObject
import ir.aliranjbarzadeh.finances.data.models.Transaction
import java.util.Date

@Entity(tableName = "transactions")
class TransactionModel(
	@PrimaryKey(autoGenerate = true)
	val id: Long = 0,

	@ColumnInfo(name = "bank_id")
	var bankId: Long,

	@ColumnInfo(name = "card_id")
	var cardId: Long,

	@ColumnInfo(name = "category_id")
	var categoryId: Long,

	@ColumnInfo(name = "contact_id")
	var contactId: Long?,

	@ColumnInfo(name = "price")
	val price: Long,

	@ColumnInfo(name = "type")
	val type: String,

	@ColumnInfo(name = "description")
	val description: String,

	@ColumnInfo(name = "created_at")
	val createdAt: Date,

	@ColumnInfo(name = "updated_at")
	val updatedAt: Date,

	@ColumnInfo(name = "deleted_at")
	val deletedAt: Date? = null,
) : ResponseObject<Transaction> {

	companion object {
		fun fromModel(transaction: Transaction): TransactionModel = TransactionModel(
			id = transaction.id,
			bankId = transaction.bankId,
			cardId = transaction.cardId,
			categoryId = transaction.categoryId,
			contactId = transaction.contactId,
			price = transaction.price,
			type = transaction.type,
			description = transaction.description,
			createdAt = transaction.createdAt,
			updatedAt = transaction.updatedAt
		)
	}

	override fun toDomain(): Transaction = Transaction(
		id = id,
		bankId = bankId,
		cardId = cardId,
		categoryId = categoryId,
		contactId = contactId,
		price = price,
		type = type,
		description = description,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}