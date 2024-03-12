package ir.aliranjbarzadeh.finances.data.repositories

import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.domain.repositories.CardRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepositoryImpl @Inject constructor(private val dataSource: CardDataSource) : CardRepository {
	override suspend fun list(): List<Card> = dataSource.list()
	override suspend fun store(card: Card): Long = dataSource.store(card)
	override suspend fun update(card: Card): Int = dataSource.update(card)
	override suspend fun destroy(card: Card): Int = dataSource.destroy(card)
}