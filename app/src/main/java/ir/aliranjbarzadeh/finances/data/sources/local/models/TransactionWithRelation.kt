package ir.aliranjbarzadeh.finances.data.sources.local.models

import androidx.room.Embedded
import androidx.room.Relation
import ir.aliranjbarzadeh.finances.data.base.ResponseObject
import ir.aliranjbarzadeh.finances.data.models.Transaction

data class TransactionWithRelation(
	@Embedded val transactionModel: TransactionModel,

	@Relation(parentColumn = "card_id", entityColumn = "id")
	val cardModel: CardModel,

	@Relation(parentColumn = "category_id", entityColumn = "id")
	val categoryModel: CategoryModel,
) : ResponseObject<Transaction> {
	override fun toDomain(): Transaction {
		val transaction = transactionModel.toDomain()
		transaction.card = cardModel.toDomain()
		transaction.category = categoryModel.toDomain()

		return transaction
	}
}
