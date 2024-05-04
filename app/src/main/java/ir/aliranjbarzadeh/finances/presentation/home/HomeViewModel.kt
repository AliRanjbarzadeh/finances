package ir.aliranjbarzadeh.finances.presentation.home

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.aliranjbarzadeh.finances.base.BaseViewModel
import ir.aliranjbarzadeh.finances.base.di.Logger
import ir.aliranjbarzadeh.finances.base.dispatchers.DispatchersProvider
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.data.models.Filter
import ir.aliranjbarzadeh.finances.data.models.Transaction
import ir.aliranjbarzadeh.finances.domain.usecases.card.CardListUseCase
import ir.aliranjbarzadeh.finances.domain.usecases.transaction.TransactionListUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	dispatchersProvider: DispatchersProvider,
	private val logger: Logger,
	private val transactionListUseCase: TransactionListUseCase,
	private val cardListUseCase: CardListUseCase,
) : BaseViewModel(dispatchersProvider) {

	private val _transactions: MutableLiveData<List<Transaction>> = MutableLiveData()
	private val _cards: MutableLiveData<List<Card>> = MutableLiveData()

	init {
		fetchTransactions()
	}

	fun fetchTransactions(filters: MutableList<Filter> = mutableListOf()) {
		execute {
			_isLoading.postValue(true)
			val result = transactionListUseCase(filters)
			_transactions.postValue(result)
			_isEmptyList.postValue(result.isEmpty())
			_isLoading.postValue(false)
			isFirstRun = false
		}
	}

	fun transactions(): MutableLiveData<List<Transaction>> = _transactions

	fun fetchCards() {
		execute {
			val result = cardListUseCase()
			_cards.postValue(result)
		}
	}

	fun cards(): MutableLiveData<List<Card>> = _cards

	fun updateView() {
		_isEmptyList.postValue(_transactions.value?.isEmpty() ?: true)
	}
}