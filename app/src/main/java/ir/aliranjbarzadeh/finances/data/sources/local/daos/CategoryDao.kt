package ir.aliranjbarzadeh.finances.data.sources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ir.aliranjbarzadeh.finances.data.sources.local.models.CategoryModel

@Dao
interface CategoryDao {
	@Query("SELECT * FROM categories WHERE deleted_at IS NULL ORDER BY id DESC")
	suspend fun list(): List<CategoryModel>

	@Query("SELECT * FROM categories WHERE name = :categoryName LIMIT 1")
	suspend fun findByName(categoryName: String): CategoryModel?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun store(categoryModel: CategoryModel): Long

	@Update(onConflict = OnConflictStrategy.IGNORE)
	suspend fun update(categoryModel: CategoryModel): Int

	@Query("UPDATE banks SET deleted_at = DATE('now') WHERE id = :categoryId")
	suspend fun destroy(categoryId: Long): Int
}