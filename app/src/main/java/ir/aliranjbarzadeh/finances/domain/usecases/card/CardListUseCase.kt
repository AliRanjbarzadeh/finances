package ir.aliranjbarzadeh.finances.domain.usecases.card

import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.domain.repositories.CardRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardListUseCase @Inject constructor(
	private val repository: CardRepository,
) {
	suspend operator fun invoke(): List<Card> = repository.list()
}