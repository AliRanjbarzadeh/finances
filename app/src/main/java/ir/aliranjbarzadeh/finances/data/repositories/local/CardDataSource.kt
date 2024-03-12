package ir.aliranjbarzadeh.finances.data.repositories.local

import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.data.repositories.CardDataSource
import ir.aliranjbarzadeh.finances.data.sources.local.daos.CardDao
import ir.aliranjbarzadeh.finances.data.sources.local.models.CardModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardDataSource @Inject constructor(private val dao: CardDao) : CardDataSource {
	override suspend fun list(): List<Card> {
		val items = dao.list();
		return items.map { it.toDomain() }
	}

	override suspend fun store(card: Card): Long = dao.store(CardModel.fromModel(card))

	override suspend fun update(card: Card): Int = dao.update(CardModel.fromModel(card))

	override suspend fun destroy(card: Card): Int = dao.destroy(card.id)
}