package ir.aliranjbarzadeh.finances.data.repositories.local

import ir.aliranjbarzadeh.finances.base.util.Logger
import ir.aliranjbarzadeh.finances.base.extensions.fixArabic
import ir.aliranjbarzadeh.finances.data.models.Category
import ir.aliranjbarzadeh.finances.data.repositories.CategoryDataSource
import ir.aliranjbarzadeh.finances.data.sources.local.daos.CategoryDao
import ir.aliranjbarzadeh.finances.data.sources.local.models.CategoryModel
import ir.aliranjbarzadeh.finances.presentation.TransactionType
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryDataSource @Inject constructor(private val dao: CategoryDao, private val logger: Logger) : CategoryDataSource {
	override suspend fun initializeData() {
		val categoriesWithdraw = listOf(
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

		val categoriesDeposit = listOf(
			"حقوق",
			"پاداش",
			"عیدی",
			"طلب",
			"سایر",
		)

		logger.debug("Category seeder started", "CATEGORY_SEEDER")

		val currentCategories = dao.all()
		val currentTime = Date()
		categoriesWithdraw.forEach { categoryName ->
			val currentCategory = currentCategories.filter { it.type == TransactionType.WITHDRAW }.find { it.name == categoryName }
			if (currentCategory == null) {
				dao.store(CategoryModel(name = categoryName.fixArabic().trim(), type = TransactionType.WITHDRAW, isDeletable = false, createdAt = currentTime, updatedAt = currentTime))
			}
		}

		categoriesDeposit.forEach { categoryName ->
			val currentCategory = currentCategories.filter { it.type == TransactionType.DEPOSIT }.find { it.name == categoryName }
			if (currentCategory == null) {
				dao.store(CategoryModel(name = categoryName.fixArabic().trim(), type = TransactionType.DEPOSIT, isDeletable = false, createdAt = currentTime, updatedAt = currentTime))
			}
		}

		logger.debug("Category seeder done", "CATEGORY_SEEDER")
	}

	override suspend fun list(type: TransactionType): List<Category> {
		val items = dao.list(type)
		return items.map { it.toDomain() }
	}

	override suspend fun store(category: Category): Long = dao.store(CategoryModel.fromModel(category))

	override suspend fun update(category: Category): Int = dao.update(CategoryModel.fromModel(category))

	override suspend fun destroy(category: Category): Int = dao.destroy(category.id)
}