package ir.aliranjbarzadeh.finances.data.repositories.local

import ir.aliranjbarzadeh.finances.base.di.Logger
import ir.aliranjbarzadeh.finances.base.extensions.fixArabic
import ir.aliranjbarzadeh.finances.data.models.Bank
import ir.aliranjbarzadeh.finances.data.repositories.BankDataSource
import ir.aliranjbarzadeh.finances.data.sources.local.daos.BankDao
import ir.aliranjbarzadeh.finances.data.sources.local.models.BankModel
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BankDataSource @Inject constructor(private val dao: BankDao, private val logger: Logger) : BankDataSource {
	override suspend fun initializeData() {
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

		logger.debug("Bank seeder started", "BANK_SEEDER")

		val currentBanks = dao.list()
		val currentTime = Date()
		banks.forEach { bankName ->
			val currentBank = currentBanks.find { it.name.equals(bankName) }
			if (currentBank == null) {
				dao.store(BankModel(name = bankName.fixArabic().trim(), createdAt = currentTime, updatedAt = currentTime))
			}
		}

		logger.debug("Bank seeder done", "BANK_SEEDER")
	}

	override suspend fun list(): List<Bank> {
		val items = dao.list();
		return items.map { it.toDomain() }
	}

	override suspend fun store(bank: Bank): Long = dao.store(BankModel.fromModel(bank))

	override suspend fun update(bank: Bank): Int = dao.update(BankModel.fromModel(bank))

	override suspend fun destroy(bank: Bank): Int = dao.destroy(bank.id)
}