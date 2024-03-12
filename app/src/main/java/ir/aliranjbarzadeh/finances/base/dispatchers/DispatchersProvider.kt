package ir.aliranjbarzadeh.finances.base.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
	fun getIO(): CoroutineDispatcher
	fun getMain(): CoroutineDispatcher
	fun getDefault(): CoroutineDispatcher
}