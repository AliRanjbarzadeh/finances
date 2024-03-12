package ir.aliranjbarzadeh.finances.data.sources.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.aliranjbarzadeh.finances.base.helpers.DateTimeHelper
import ir.aliranjbarzadeh.finances.data.base.ResponseObject
import ir.aliranjbarzadeh.finances.data.models.Bank
import java.util.Date

@Entity(tableName = "banks")
data class BankModel(
	@PrimaryKey(autoGenerate = true)
	val id: Long = 0,

	@ColumnInfo(name = "name")
	var name: String,

	@ColumnInfo(name = "created_at")
	var createdAt: Date?,

	@ColumnInfo(name = "updated_at")
	var updatedAt: Date?,

	@ColumnInfo(name = "deleted_at")
	var deletedAt: Date? = null,
) : ResponseObject<Bank> {

	companion object {
		fun fromModel(bank: Bank): BankModel = BankModel(
			id = bank.id,
			name = bank.name,
			createdAt = DateTimeHelper.currentDateUTC(),
			updatedAt = DateTimeHelper.currentDateUTC(),
		)
	}

	override fun toDomain(): Bank = Bank(
		id = id,
		name = name
	)
}
