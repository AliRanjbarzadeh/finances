package ir.aliranjbarzadeh.finances.data.sources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ir.aliranjbarzadeh.finances.data.sources.local.models.CategoryModel
import ir.aliranjbarzadeh.finances.presentation.TransactionType

@Dao
interface CategoryDao {
	@Query("SELECT * FROM categories WHERE deleted_at IS NULL ORDER BY is_deletable DESC, priority ASC")
	suspend fun all(): List<CategoryModel>

	@Query("SELECT * FROM categories WHERE deleted_at IS NULL AND type = :type ORDER BY is_deletable DESC, priority ASC")
	suspend fun list(type: TransactionType): List<CategoryModel>

	@Query("SELECT * FROM categories WHERE name = :categoryName LIMIT 1")
	suspend fun findByName(categoryName: String): CategoryModel?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun store(categoryModel: CategoryModel): Long

	@Update(onConflict = OnConflictStrategy.IGNORE)
	suspend fun update(categoryModel: CategoryModel): Int

	@Query("UPDATE banks SET deleted_at = DATE('now') WHERE id = :categoryId")
	suspend fun destroy(categoryId: Long): Int
}