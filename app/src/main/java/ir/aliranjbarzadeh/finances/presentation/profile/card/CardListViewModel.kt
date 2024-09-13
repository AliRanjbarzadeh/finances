package ir.aliranjbarzadeh.finances.presentation.profile.card

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.aliranjbarzadeh.finances.base.BaseViewModel
import ir.aliranjbarzadeh.finances.base.util.Logger
import ir.aliranjbarzadeh.finances.base.dispatchers.DispatchersProvider
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.domain.usecases.card.CardListUseCase
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(
	dispatchersProvider: DispatchersProvider,
	private val logger: Logger,
	private val cardListUseCase: CardListUseCase,
) : BaseViewModel(dispatchersProvider) {

	private val _cards: MutableLiveData<List<Card>> = MutableLiveData()

	init {
		fetchCards()
	}

	fun fetchCards() {
		execute {
			_isLoading.postValue(true)
			val result = cardListUseCase()
			_cards.postValue(result)
			_isEmptyList.postValue(result.isEmpty())
			_isLoading.postValue(false)
			isFirstRun = false
		}
	}

	fun cards(): MutableLiveData<List<Card>> = _cards
}