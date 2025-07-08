package ir.aliranjbarzadeh.finances.data.sources.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface GlobalDao {
	fun deleteAll() {
		deleteCards()
		deleteBanks()
	}

	@Query("DELETE FROM cards WHERE 1")
	fun deleteCards()

	@Query("DELETE FROM banks WHERE 1")
	fun deleteBanks()
}