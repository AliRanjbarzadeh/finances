package ir.aliranjbarzadeh.finances.data.repositories.local

import ir.aliranjbarzadeh.finances.data.models.Transaction
import ir.aliranjbarzadeh.finances.data.repositories.TransactionDataSource
import ir.aliranjbarzadeh.finances.data.sources.local.daos.CardDao
import ir.aliranjbarzadeh.finances.data.sources.local.daos.TransactionDao
import ir.aliranjbarzadeh.finances.data.sources.local.models.TransactionModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionDataSource @Inject constructor(private val dao: TransactionDao, private val cardDao: CardDao) : TransactionDataSource {
	override suspend fun list(): List<Transaction> {
		val items = dao.list();
		return items.map { it.toDomain() }
	}

	override suspend fun store(transaction: Transaction): Long {
		val transactionId: Long = dao.store(TransactionModel.fromModel(transaction))
		cardDao.updateBalances(transaction.cardId)
		return transactionId
	}

	override suspend fun update(transaction: Transaction): Int = dao.update(TransactionModel.fromModel(transaction))

	override suspend fun destroy(transaction: Transaction): Int = dao.destroy(transaction.id)
}