package ir.aliranjbarzadeh.finances.data.sources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ir.aliranjbarzadeh.finances.data.sources.local.models.BankModel

@Dao
interface BankDao {
	@Query("SELECT * FROM banks WHERE deleted_at IS NULL ORDER BY name")
	suspend fun list(): List<BankModel>

	@Query("SELECT * FROM banks WHERE name = :bankeName LIMIT 1")
	suspend fun findByName(bankeName: String): BankModel?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun store(bankModel: BankModel): Long

	@Update(onConflict = OnConflictStrategy.IGNORE)
	suspend fun update(bankModel: BankModel): Int

	@Query("UPDATE banks SET deleted_at = DATE('now') WHERE id = :bankId")
	suspend fun destroy(bankId: Long): Int
}