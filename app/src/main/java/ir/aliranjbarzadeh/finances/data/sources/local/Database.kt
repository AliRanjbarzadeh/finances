package ir.aliranjbarzadeh.finances.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.aliranjbarzadeh.finances.data.sources.local.daos.BankDao
import ir.aliranjbarzadeh.finances.data.sources.local.daos.CardDao
import ir.aliranjbarzadeh.finances.data.sources.local.daos.CategoryDao
import ir.aliranjbarzadeh.finances.data.sources.local.daos.TransactionDao
import ir.aliranjbarzadeh.finances.data.sources.local.models.BankModel
import ir.aliranjbarzadeh.finances.data.sources.local.models.CardModel
import ir.aliranjbarzadeh.finances.data.sources.local.models.CategoryModel
import ir.aliranjbarzadeh.finances.data.sources.local.models.TransactionModel

@Database(entities = [CardModel::class, BankModel::class, CategoryModel::class, TransactionModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
	abstract val cardDao: CardDao
	abstract val bankDao: BankDao
	abstract val categoryDao: CategoryDao
	abstract val transactionDao: TransactionDao
}