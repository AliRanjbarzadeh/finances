package ir.aliranjbarzadeh.finances.data.sources.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.aliranjbarzadeh.finances.data.base.ResponseObject
import ir.aliranjbarzadeh.finances.data.models.Card
import java.util.Date

@Entity(tableName = "cards")
class CardModel(
	@PrimaryKey(autoGenerate = true)
	val id: Long = 0,

	@ColumnInfo(name = "bank_id")
	val bankId: Long = 0,

	@ColumnInfo(name = "name")
	val name: String,

	@ColumnInfo(name = "balance")
	val balance: Long = 0,

	@ColumnInfo(name = "deposit")
	val deposit: Long = 0,

	@ColumnInfo(name = "withdraw")
	val withdraw: Long = 0,

	@ColumnInfo(name = "is_default")
	val isDefault: Boolean,

	@ColumnInfo(name = "created_at")
	val createdAt: Date,

	@ColumnInfo(name = "updated_at")
	val updatedAt: Date,

	@ColumnInfo(name = "deleted_at")
	val deletedAt: Date? = null,
) : ResponseObject<Card> {

	companion object {
		fun fromModel(card: Card): CardModel = CardModel(
			id = card.id,
			bankId = card.bankId,
			name = card.name,
			balance = card.balance,
			isDefault = card.isDefault,
			createdAt = card.createdAt,
			updatedAt = card.updatedAt
		)
	}

	override fun toDomain(): Card = Card(
		id = id,
		bankId = bankId,
		name = name,
		balance = balance,
		deposit = deposit,
		withdraw = withdraw,
		isDefault = isDefault,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}