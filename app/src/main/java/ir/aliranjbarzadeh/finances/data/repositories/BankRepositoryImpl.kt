package ir.aliranjbarzadeh.finances.data.repositories

import ir.aliranjbarzadeh.finances.data.models.Bank
import ir.aliranjbarzadeh.finances.domain.repositories.BankRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BankRepositoryImpl @Inject constructor(private val dataSource: BankDataSource) : BankRepository {
	override suspend fun initializeData() = dataSource.initializeData()
	override suspend fun list(): List<Bank> = dataSource.list()
	override suspend fun store(bank: Bank): Long = dataSource.store(bank)
	override suspend fun update(bank: Bank): Int = dataSource.update(bank)
	override suspend fun destroy(bank: Bank): Int = dataSource.destroy(bank)
}