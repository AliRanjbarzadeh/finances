package ir.aliranjbarzadeh.finances.domain.usecases

import ir.aliranjbarzadeh.finances.domain.repositories.BankRepository
import ir.aliranjbarzadeh.finances.domain.repositories.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitializeDatabaseUseCase @Inject constructor(
	private val bankRepository: BankRepository,
	private val categoryRepository: CategoryRepository,
) {
	suspend operator fun invoke() {
		bankRepository.initializeData()
		categoryRepository.initializeData()
	}
}