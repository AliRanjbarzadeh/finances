package ir.aliranjbarzadeh.finances.domain.usecases.transaction

import ir.aliranjbarzadeh.finances.data.models.Transaction
import ir.aliranjbarzadeh.finances.domain.repositories.TransactionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionUpdateUseCase @Inject constructor(private val repository: TransactionRepository) {
	suspend operator fun invoke(transaction: Transaction): Int = repository.update(transaction)
}