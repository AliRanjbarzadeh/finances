package ir.aliranjbarzadeh.finances.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ir.aliranjbarzadeh.finances.base.BaseViewModel
import ir.aliranjbarzadeh.finances.base.util.Logger
import ir.aliranjbarzadeh.finances.base.dispatchers.DispatchersProvider
import ir.aliranjbarzadeh.finances.domain.usecases.InitializeDatabaseUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val dispatchersProvider: DispatchersProvider,
	private val logger: Logger,
	private val initializeDatabaseUseCase: InitializeDatabaseUseCase,
) : BaseViewModel(dispatchersProvider) {

	fun seedDatabase() {
		execute {
			_isLoading.postValue(true)
			initializeDatabaseUseCase()
			_isLoading.postValue(false)
		}
	}
}