package ir.aliranjbarzadeh.finances.data.repositories.local

import ir.aliranjbarzadeh.finances.base.di.Logger
import ir.aliranjbarzadeh.finances.base.extensions.fixArabic
import ir.aliranjbarzadeh.finances.data.models.Category
import ir.aliranjbarzadeh.finances.data.repositories.CategoryDataSource
import ir.aliranjbarzadeh.finances.data.sources.local.daos.CategoryDao
import ir.aliranjbarzadeh.finances.data.sources.local.models.CategoryModel
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryDataSource @Inject constructor(private val dao: CategoryDao, private val logger: Logger) : CategoryDataSource {
	override suspend fun initializeData() {
		val categoriesOutcome = listOf(
			"خوردنی",
			"خریدنی",
			"رفت و آمد",
			"خونه",
			"خوش گذرونی",
			"سلامتی",
			"هدیه",
			"خانواده",
			"قبض",
			"پوشیدنی",
			"خودرو",
			"بدهی",
			"سایر",
		)

		val categoriesIncome = listOf(
			"حقوق",
			"پاداش",
			"عیدی",
			"طلب",
			"سایر",
		)

		logger.debug("Category seeder started", "CATEGORY_SEEDER")

		val currentCategories = dao.list()
		val currentTime = Date()
		categoriesOutcome.forEach { categoryName ->
			val currentCategory = currentCategories.filter { it.type == "outcome" }.find { it.name == categoryName }
			if (currentCategory == null) {
				dao.store(CategoryModel(name = categoryName.fixArabic().trim(), type = "outcome", isDeletable = false, createdAt = currentTime, updatedAt = currentTime))
			}
		}

		categoriesIncome.forEach { categoryName ->
			val currentCategory = currentCategories.filter { it.type == "income" }.find { it.name == categoryName }
			if (currentCategory == null) {
				dao.store(CategoryModel(name = categoryName.fixArabic().trim(), type = "income", isDeletable = false, createdAt = currentTime, updatedAt = currentTime))
			}
		}

		logger.debug("Category seeder done", "CATEGORY_SEEDER")
	}

	override suspend fun list(): List<Category> {
		val items = dao.list();
		return items.map { it.toDomain() }
	}

	override suspend fun store(category: Category): Long = dao.store(CategoryModel.fromModel(category))

	override suspend fun update(category: Category): Int = dao.update(CategoryModel.fromModel(category))

	override suspend fun destroy(category: Category): Int = dao.destroy(category.id)
}