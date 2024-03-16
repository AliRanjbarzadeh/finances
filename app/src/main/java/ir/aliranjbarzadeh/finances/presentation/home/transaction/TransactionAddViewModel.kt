package ir.aliranjbarzadeh.finances.presentation.home.transaction

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseViewModel
import ir.aliranjbarzadeh.finances.base.dispatchers.DispatchersProvider
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.data.models.Category
import ir.aliranjbarzadeh.finances.data.models.Transaction
import ir.aliranjbarzadeh.finances.domain.usecases.card.CardListUseCase
import ir.aliranjbarzadeh.finances.domain.usecases.category.CategoryListUseCase
import ir.aliranjbarzadeh.finances.domain.usecases.transaction.TransactionStoreUseCase
import javax.inject.Inject

@HiltViewModel
class TransactionAddViewModel @Inject constructor(
	private val dispatchersProvider: DispatchersProvider,
	private val transactionStoreUseCase: TransactionStoreUseCase,
	private val cardListUseCase: CardListUseCase,
	private val categoryListUseCase: CategoryListUseCase,
) : BaseViewModel(dispatchersProvider) {
	private val _cards: MutableLiveData<List<Card>> = MutableLiveData()
	private val _categories: MutableLiveData<List<Category>> = MutableLiveData()

	init {
		fetchCards()
		fetchCategories()
	}

	fun storeItem(transaction: Transaction) {
		if (transaction.categoryId == 0L) {
			_error.postValue(R.string.select_category_error)
			return
		}

		if (transaction.cardId == 0L) {
			_error.postValue(R.string.select_card_error)
			return
		}

		if (transaction.price == 0L) {
			_error.postValue(R.string.amount_error)
			return
		}

		if (transaction.price < 0L) {
			_error.postValue(R.string.amount_value_error)
			return
		}

		execute {
			_isLoading.postValue(true)
			val result = transactionStoreUseCase(transaction)
			_store.postValue(result)
			_isLoading.postValue(false)
		}
	}

	private fun fetchCards() {
		execute {
			val result = cardListUseCase()
			_cards.postValue(result)
		}
	}

	fun cards(): MutableLiveData<List<Card>> = _cards

	fun fetchCategories(type: String = "deposit") {
		execute {
			val result = categoryListUseCase(type)
			_categories.postValue(result)
		}
	}

	fun categories(): MutableLiveData<List<Category>> = _categories
}