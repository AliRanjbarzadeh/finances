package ir.aliranjbarzadeh.finances.presentation.home

import dagger.hilt.android.lifecycle.HiltViewModel
import ir.aliranjbarzadeh.finances.base.BaseViewModel
import ir.aliranjbarzadeh.finances.base.util.Logger
import ir.aliranjbarzadeh.finances.base.dispatchers.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	dispatchersProvider: DispatchersProvider,
	private val logger: Logger,
) : BaseViewModel(dispatchersProvider) {
}