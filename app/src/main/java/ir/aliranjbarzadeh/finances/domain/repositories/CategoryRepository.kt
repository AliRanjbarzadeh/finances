package ir.aliranjbarzadeh.finances.domain.repositories

import ir.aliranjbarzadeh.finances.data.models.Category

interface CategoryRepository {
	suspend fun initializeData()
	suspend fun list(): List<Category>
	suspend fun store(category: Category): Long
	suspend fun update(category: Category): Int
	suspend fun destroy(category: Category): Int
}