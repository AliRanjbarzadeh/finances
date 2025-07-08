package ir.aliranjbarzadeh.finances.data.sources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.aliranjbarzadeh.finances.base.Configs
import ir.aliranjbarzadeh.finances.data.sources.local.daos.BankDao
import ir.aliranjbarzadeh.finances.data.sources.local.daos.CardDao
import ir.aliranjbarzadeh.finances.data.sources.local.daos.CategoryDao
import ir.aliranjbarzadeh.finances.data.sources.local.daos.GlobalDao
import ir.aliranjbarzadeh.finances.data.sources.local.daos.TransactionDao
import ir.aliranjbarzadeh.finances.data.sources.local.models.BankModel
import ir.aliranjbarzadeh.finances.data.sources.local.models.CardModel
import ir.aliranjbarzadeh.finances.data.sources.local.models.CategoryModel
import ir.aliranjbarzadeh.finances.data.sources.local.models.TransactionModel
import ir.aliranjbarzadeh.finances.data.sources.local.Database as BaseDB

@Database(
	entities = [CardModel::class, BankModel::class, CategoryModel::class, TransactionModel::class],
	version = 2,
	exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
	abstract val cardDao: CardDao
	abstract val bankDao: BankDao
	abstract val categoryDao: CategoryDao
	abstract val transactionDao: TransactionDao
	abstract val globalDao: GlobalDao

	companion object {
		@Volatile
		private var INSTANCE: BaseDB? = null

		fun getDatabase(context: Context): BaseDB {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context,
					BaseDB::class.java,
					Configs.DATABASE
				).build()

				INSTANCE = instance
				instance
			}
		}
	}
}