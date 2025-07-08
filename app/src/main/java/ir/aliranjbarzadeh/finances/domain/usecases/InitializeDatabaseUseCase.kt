package ir.aliranjbarzadeh.finances.domain.usecases

import ir.aliranjbarzadeh.finances.base.extensions.fixArabic
import ir.aliranjbarzadeh.finances.data.sources.local.Database
import ir.aliranjbarzadeh.finances.data.sources.local.models.BankModel
import ir.aliranjbarzadeh.finances.domain.repositories.BankRepository
import ir.aliranjbarzadeh.finances.domain.repositories.CategoryRepository
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitializeDatabaseUseCase @Inject constructor(
	private val mDatabase: Database,
	private val bankRepository: BankRepository,
	private val categoryRepository: CategoryRepository,
) {
	suspend operator fun invoke() {
		val banks = listOf(
			"توسعه تعاون",
			"توسعه صادرات ایران",
			"سپه",
			"صنعت و معدن",
			"کشاورزی",
			"مسکن",
			"ملی ایران",
			"پست بانک ایران",
			"اقتصاد نوین",
			"ایران زمین",
			"پارسیان",
			"پاسارگاد",
			"تجارت",
			"خاورمیانه",
			"دی",
			"سامان",
			"سرمایه",
			"سینا",
			"شهر",
			"صادرات",
			"قرض‌الحسنه رسالت",
			"کارآفرین",
			"گردشگری",
			"ملت",
			"آینده",
			"قرض‌الحسنه مهر ایران",
			"رفاه کارگران"
		)

		val currentBanks = mDatabase.bankDao.list()
		val currentTime = Date()
		mDatabase.runInTransaction {
			banks.forEach { bankName ->
				val currentBank = currentBanks.find { it.name == bankName.fixArabic().trim() }
				if (currentBank == null) {
					mDatabase.bankDao.store2(BankModel(name = bankName.fixArabic().trim(), createdAt = currentTime, updatedAt = currentTime))
				}
			}
		}
//		bankRepository.initializeData()
//		categoryRepository.initializeData()
	}
}