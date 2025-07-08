package ir.aliranjbarzadeh.finances.domain.usecases

import ir.aliranjbarzadeh.finances.domain.repositories.BankRepository
import ir.aliranjbarzadeh.finances.domain.repositories.CardRepository
import ir.aliranjbarzadeh.finances.domain.repositories.CategoryRepository
import ir.aliranjbarzadeh.finances.domain.repositories.TransactionRepository
import ir.aliranjbarzadeh.finances.presentation.TransactionType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupDatabaseUseCase @Inject constructor(
	private val bankRepository: BankRepository,
	private val categoryRepository: CategoryRepository,
	private val cardRepository: CardRepository,
	private val transactionRepository: TransactionRepository,
) {
	suspend operator fun invoke() {
		val banks = bankRepository.list()
		val depositCategories = categoryRepository.list(TransactionType.DEPOSIT)
		val withdrawCategories = categoryRepository.list(TransactionType.WITHDRAW)
		val cards = cardRepository.list()
		val transactions = transactionRepository.all()

	}
}