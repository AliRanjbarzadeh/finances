package ir.aliranjbarzadeh.finances.data.repositories.local

import androidx.sqlite.db.SimpleSQLiteQuery
import ir.aliranjbarzadeh.finances.data.models.Filter
import ir.aliranjbarzadeh.finances.data.models.Transaction
import ir.aliranjbarzadeh.finances.data.repositories.TransactionDataSource
import ir.aliranjbarzadeh.finances.data.sources.local.daos.CardDao
import ir.aliranjbarzadeh.finances.data.sources.local.daos.TransactionDao
import ir.aliranjbarzadeh.finances.data.sources.local.models.TransactionModel
import ir.aliranjbarzadeh.finances.presentation.FilterType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionDataSource @Inject constructor(private val dao: TransactionDao, private val cardDao: CardDao) : TransactionDataSource {
	override suspend fun list(filters: MutableList<Filter>): List<Transaction> {
		val query = StringBuilder("SELECT * FROM transactions WHERE deleted_at IS NULL")
		filters.forEach {
			when (it.type) {
				FilterType.TRANSACTION_TYPE -> query.append(" AND type = '${it.value}'")

				FilterType.CATEGORY -> query.append(" AND category_id = ${it.value.toLong()}")

				FilterType.CARD -> query.append(" AND card_id = ${it.value.toLong()}")
			}
		}
		query.append(" ORDER BY id DESC")
		val items = dao.list(SimpleSQLiteQuery(query.toString()));
		return items.map { it.toDomain() }
	}

	override suspend fun store(transaction: Transaction): Long {
		val transactionId: Long = dao.store(TransactionModel.fromModel(transaction))
		cardDao.updateBalances(transaction.cardId)
		return transactionId
	}

	override suspend fun update(transaction: Transaction): Int {
		val rowsAffected = dao.update(TransactionModel.fromModel(transaction))
		cardDao.updateBalances(transaction.cardId)
		return rowsAffected
	}

	override suspend fun destroy(transaction: Transaction): Int = dao.destroy(transaction.id)
}