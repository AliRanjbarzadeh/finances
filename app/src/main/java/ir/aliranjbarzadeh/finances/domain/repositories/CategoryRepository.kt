package ir.aliranjbarzadeh.finances.domain.repositories

import ir.aliranjbarzadeh.finances.data.models.Category
import ir.aliranjbarzadeh.finances.presentation.TransactionType

interface CategoryRepository {
	suspend fun initializeData()
	suspend fun list(type: TransactionType): List<Category>
	suspend fun store(category: Category): Long
	suspend fun update(category: Category): Int
	suspend fun destroy(category: Category): Int
}