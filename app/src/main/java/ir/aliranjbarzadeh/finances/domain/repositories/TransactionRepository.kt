package ir.aliranjbarzadeh.finances.domain.repositories

import ir.aliranjbarzadeh.finances.data.models.Transaction

interface TransactionRepository {
	suspend fun list(): List<Transaction>
	suspend fun store(transaction: Transaction): Long
	suspend fun update(transaction: Transaction): Int
	suspend fun destroy(transaction: Transaction): Int
}