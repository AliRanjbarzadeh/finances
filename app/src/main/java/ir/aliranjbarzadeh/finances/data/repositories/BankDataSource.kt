package ir.aliranjbarzadeh.finances.data.repositories

import ir.aliranjbarzadeh.finances.data.models.Bank

interface BankDataSource {
	suspend fun initializeData()
	suspend fun list(): List<Bank>
	suspend fun store(bank: Bank): Long
	suspend fun update(bank: Bank): Int
	suspend fun destroy(bank: Bank): Int
}