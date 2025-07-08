package ir.aliranjbarzadeh.finances.presentation

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseViewModel
import ir.aliranjbarzadeh.finances.base.dispatchers.DispatchersProvider
import ir.aliranjbarzadeh.finances.base.util.Logger
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.domain.usecases.GlobalUseCase
import ir.aliranjbarzadeh.finances.domain.usecases.InitializeDatabaseUseCase
import ir.aliranjbarzadeh.finances.domain.usecases.card.CardStoreUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val dispatchersProvider: DispatchersProvider,
	private val logger: Logger,
	private val initializeDatabaseUseCase: InitializeDatabaseUseCase,
	private val cardStoreUseCase: CardStoreUseCase,
	private val globalUseCase: GlobalUseCase,
) : BaseViewModel(dispatchersProvider) {

	protected val _resetDatabase: MutableLiveData<Boolean> = MutableLiveData()

	fun seedDatabase() {
		execute {
			_isLoading.postValue(true)
			initializeDatabaseUseCase()
			_isLoading.postValue(false)
		}
	}

	fun storeCard() {
		val card = Card.emptyObject(false)
		card.bankId = 400
		card.name = "salam"

		execute {
			try {
				val id = cardStoreUseCase(card)
				_store.postValue(id)
			} catch (e: Exception) {
				logger.error(e.stackTraceToString(), "CARD_ADD")
				_error.postValue(R.string.card_name_error)
			}
		}
	}

	fun resetDatabase() {
		execute {
			try {
				globalUseCase()
				_resetDatabase.postValue(true)
			} catch (e: Exception) {
				_resetDatabase.postValue(false)
			}
		}
	}

	fun resetDB(): MutableLiveData<Boolean> = _resetDatabase
}