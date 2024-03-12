package ir.aliranjbarzadeh.finances.domain.repositories

import ir.aliranjbarzadeh.finances.data.models.Bank

interface BankRepository {
	suspend fun initializeData()
	suspend fun list(): List<Bank>
	suspend fun store(bank: Bank): Long
	suspend fun update(bank: Bank): Int
	suspend fun destroy(bank: Bank): Int
}