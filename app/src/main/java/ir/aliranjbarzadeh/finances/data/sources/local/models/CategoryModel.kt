package ir.aliranjbarzadeh.finances.data.sources.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.aliranjbarzadeh.finances.data.base.ResponseObject
import ir.aliranjbarzadeh.finances.data.models.Category
import ir.aliranjbarzadeh.finances.presentation.TransactionType
import java.util.Date

@Entity(tableName = "categories")
class CategoryModel(
	@PrimaryKey(autoGenerate = true)
	val id: Long = 0,

	@ColumnInfo(name = "name")
	val name: String,

	@ColumnInfo(name = "type")
	var type: TransactionType,

	@ColumnInfo(name = "is_deletable")
	val isDeletable: Boolean,

	@ColumnInfo(name = "created_at")
	val createdAt: Date,

	@ColumnInfo(name = "updated_at")
	val updatedAt: Date,

	@ColumnInfo(name = "deleted_at")
	val deletedAt: Date? = null,
) : ResponseObject<Category> {

	companion object {
		fun fromModel(category: Category): CategoryModel = CategoryModel(
			id = category.id,
			name = category.name,
			type = category.type,
			isDeletable = category.isDeletable,
			createdAt = category.createdAt,
			updatedAt = category.updatedAt
		)
	}

	override fun toDomain(): Category = Category(
		id = id,
		name = name,
		type = type,
		isDeletable = isDeletable,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}