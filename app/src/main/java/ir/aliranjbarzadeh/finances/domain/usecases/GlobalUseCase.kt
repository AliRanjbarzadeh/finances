package ir.aliranjbarzadeh.finances.domain.usecases

import ir.aliranjbarzadeh.finances.data.sources.local.Database
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalUseCase @Inject constructor(
	private val mDatabase: Database,
) {
	suspend operator fun invoke() {
		mDatabase.cardDao.deleteAll()
	}
}