package ir.aliranjbarzadeh.finances.data.repositories

import ir.aliranjbarzadeh.finances.data.models.Transaction
import ir.aliranjbarzadeh.finances.data.repositories.local.TransactionDataSource
import ir.aliranjbarzadeh.finances.domain.repositories.TransactionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(private val dataSource: TransactionDataSource) : TransactionRepository {
	override suspend fun list(): List<Transaction> = dataSource.list()
	override suspend fun store(transaction: Transaction): Long = dataSource.store(transaction)
	override suspend fun update(transaction: Transaction): Int = dataSource.update(transaction)
	override suspend fun destroy(transaction: Transaction): Int = dataSource.destroy(transaction)
}