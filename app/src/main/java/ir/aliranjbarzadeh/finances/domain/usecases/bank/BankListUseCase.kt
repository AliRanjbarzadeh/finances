package ir.aliranjbarzadeh.finances.domain.usecases.bank

import ir.aliranjbarzadeh.finances.data.models.Bank
import ir.aliranjbarzadeh.finances.domain.repositories.BankRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BankListUseCase @Inject constructor(private val repository: BankRepository) {
	suspend operator fun invoke(): List<Bank> = repository.list()
}