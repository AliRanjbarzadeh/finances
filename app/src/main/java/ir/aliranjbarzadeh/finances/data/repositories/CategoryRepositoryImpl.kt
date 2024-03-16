package ir.aliranjbarzadeh.finances.data.repositories

import ir.aliranjbarzadeh.finances.data.models.Category
import ir.aliranjbarzadeh.finances.domain.repositories.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(private val dataSource: CategoryDataSource) : CategoryRepository {
	override suspend fun initializeData() = dataSource.initializeData()
	override suspend fun list(type: String): List<Category> = dataSource.list(type)
	override suspend fun store(category: Category): Long = dataSource.store(category)
	override suspend fun update(category: Category): Int = dataSource.update(category)
	override suspend fun destroy(category: Category): Int = dataSource.destroy(category)
}