package ir.aliranjbarzadeh.finances.data.models

import ir.aliranjbarzadeh.finances.domain.network.HttpErrors


data class ErrorMessage(
	val message: String?,
	val status: HttpErrors,
)