package ir.aliranjbarzadeh.finances.base.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.aliranjbarzadeh.finances.data.sources.local.Database
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
	@Provides
	@Singleton
	fun providesCardDao(database: Database) = database.cardDao

	@Provides
	@Singleton
	fun providesBankDao(database: Database) = database.bankDao

	@Provides
	@Singleton
	fun providesCategoryDao(database: Database) = database.categoryDao

	@Provides
	@Singleton
	fun providesTransactionDao(database: Database) = database.transactionDao
}