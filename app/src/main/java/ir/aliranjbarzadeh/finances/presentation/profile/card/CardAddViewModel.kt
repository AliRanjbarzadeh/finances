package ir.aliranjbarzadeh.finances.presentation.profile.card

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseViewModel
import ir.aliranjbarzadeh.finances.base.dispatchers.DispatchersProvider
import ir.aliranjbarzadeh.finances.data.models.Bank
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.domain.usecases.bank.BankListUseCase
import ir.aliranjbarzadeh.finances.domain.usecases.card.CardStoreUseCase
import javax.inject.Inject

@HiltViewModel
class CardAddViewModel @Inject constructor(
	private val dispatchersProvider: DispatchersProvider,
	private val bankListUseCase: BankListUseCase,
	private val cardStoreUseCase: CardStoreUseCase,
) : BaseViewModel(dispatchersProvider) {
	private val _banks: MutableLiveData<List<Bank>> = MutableLiveData()

	init {
		fetchBanks()
	}

	private fun fetchBanks() {
		_isLoading.postValue(true)
		execute {
			val result = bankListUseCase()
			_banks.postValue(result)
			_isLoading.postValue(false)
		}
	}

	fun banks(): MutableLiveData<List<Bank>> = _banks

	fun storeItem(card: Card) {
		if (card.bankId <= 0) {
			_error.postValue(R.string.select_bank_error)
			return
		}
		if (card.name.isEmpty()) {
			_error.postValue(R.string.card_name_error)
			return
		}
		execute {
			_isLoading.postValue(true)
			val result = cardStoreUseCase(card)
			_store.postValue(result)
			_isLoading.postValue(false)
		}
	}
}