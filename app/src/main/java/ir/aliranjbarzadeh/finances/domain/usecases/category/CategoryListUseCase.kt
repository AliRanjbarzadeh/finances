package ir.aliranjbarzadeh.finances.domain.usecases.category

import ir.aliranjbarzadeh.finances.data.models.Category
import ir.aliranjbarzadeh.finances.domain.repositories.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryListUseCase @Inject constructor(private val repository: CategoryRepository) {
	suspend operator fun invoke(type: String): List<Category> = repository.list(type)
}