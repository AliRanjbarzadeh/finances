package ir.aliranjbarzadeh.finances.data.repositories

import ir.aliranjbarzadeh.finances.data.models.Card

interface CardDataSource {
	suspend fun list(): List<Card>
	suspend fun store(card: Card): Long
	suspend fun update(card: Card): Int
	suspend fun destroy(card: Card): Int
}