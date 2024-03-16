package ir.aliranjbarzadeh.finances.data.sources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ir.aliranjbarzadeh.finances.data.sources.local.models.TransactionModel
import ir.aliranjbarzadeh.finances.data.sources.local.models.TransactionWithRelation

@Dao
interface TransactionDao {
	@Transaction
	@Query("SELECT * FROM transactions WHERE deleted_at IS NULL ORDER BY id DESC")
	suspend fun list(): List<TransactionWithRelation>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun store(transactionModel: TransactionModel): Long

	@Update(onConflict = OnConflictStrategy.IGNORE)
	suspend fun update(transactionModel: TransactionModel): Int

	@Query("UPDATE cards SET deleted_at = DATE('now') WHERE id = :transactionId")
	suspend fun destroy(transactionId: Long): Int
}