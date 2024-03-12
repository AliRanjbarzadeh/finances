package ir.aliranjbarzadeh.finances.base.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.aliranjbarzadeh.finances.data.repositories.BankRepositoryImpl
import ir.aliranjbarzadeh.finances.data.repositories.CardRepositoryImpl
import ir.aliranjbarzadeh.finances.data.repositories.CategoryRepositoryImpl
import ir.aliranjbarzadeh.finances.data.repositories.TransactionRepositoryImpl
import ir.aliranjbarzadeh.finances.data.repositories.local.BankDataSource
import ir.aliranjbarzadeh.finances.data.repositories.local.CardDataSource
import ir.aliranjbarzadeh.finances.data.repositories.local.CategoryDataSource
import ir.aliranjbarzadeh.finances.data.repositories.local.TransactionDataSource
import ir.aliranjbarzadeh.finances.domain.repositories.BankRepository
import ir.aliranjbarzadeh.finances.domain.repositories.CardRepository
import ir.aliranjbarzadeh.finances.domain.repositories.CategoryRepository
import ir.aliranjbarzadeh.finances.domain.repositories.TransactionRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
	@Provides
	fun providesCardRepository(dataSource: CardDataSource): CardRepository = CardRepositoryImpl(dataSource)

	@Provides
	fun providesBankRepository(dataSource: BankDataSource): BankRepository = BankRepositoryImpl(dataSource)

	@Provides
	fun providesCategoryRepository(dataSource: CategoryDataSource): CategoryRepository = CategoryRepositoryImpl(dataSource)

	@Provides
	fun providesTransactionRepository(dataSource: TransactionDataSource): TransactionRepository = TransactionRepositoryImpl(dataSource)
}