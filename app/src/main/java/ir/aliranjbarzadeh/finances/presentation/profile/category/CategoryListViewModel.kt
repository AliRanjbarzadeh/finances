package ir.aliranjbarzadeh.finances.presentation.profile.category

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.aliranjbarzadeh.finances.base.BaseViewModel
import ir.aliranjbarzadeh.finances.base.dispatchers.DispatchersProvider
import ir.aliranjbarzadeh.finances.data.models.Category
import ir.aliranjbarzadeh.finances.domain.usecases.category.CategoryListUseCase
import ir.aliranjbarzadeh.finances.presentation.TransactionType
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
	dispatchersProvider: DispatchersProvider,
	private val categoryListUseCase: CategoryListUseCase,
) : BaseViewModel(dispatchersProvider) {
	private val _categories: MutableLiveData<List<Category>> = MutableLiveData()

	fun fetchCategories(transactionType: TransactionType) {
		execute {
			_isLoading.postValue(true)
			val result = categoryListUseCase(transactionType)
			_categories.postValue(result)
			_isEmptyList.postValue(result.isEmpty())
			_isLoading.postValue(false)
		}
	}

	fun categories(): MutableLiveData<List<Category>> = _categories
}