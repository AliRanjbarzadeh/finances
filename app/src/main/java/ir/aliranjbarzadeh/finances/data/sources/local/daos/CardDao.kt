package ir.aliranjbarzadeh.finances.data.sources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ir.aliranjbarzadeh.finances.data.sources.local.models.CardModel

@Dao
interface CardDao {
	@Query("SELECT * FROM cards WHERE deleted_at IS NULL ORDER BY id DESC")
	suspend fun list(): List<CardModel>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun store(cardModel: CardModel): Long

	@Update(onConflict = OnConflictStrategy.IGNORE)
	suspend fun update(cardModel: CardModel): Int

	@Query("UPDATE cards SET deleted_at = DATE('now') WHERE id = :cardId")
	suspend fun destroy(cardId: Long): Int

	@Query("UPDATE cards SET deposit = (SELECT IFNULL(SUM(price), 0) FROM transactions WHERE card_id = :cardId AND type = 'deposit'), withdraw = (SELECT IFNULL(SUM(price), 0) FROM transactions WHERE card_id = :cardId AND type = 'withdraw') WHERE id = :cardId")
	suspend fun updateBalances(cardId: Long)
}