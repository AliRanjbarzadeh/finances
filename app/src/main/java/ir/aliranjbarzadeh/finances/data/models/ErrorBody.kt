package ir.aliranjbarzadeh.finances.data.models

import ir.aliranjbarzadeh.finances.data.base.ResponseObject
import ir.aliranjbarzadeh.finances.domain.network.HttpErrors


data class ErrorBody(
	val message: String?,
	val status: Int?,
) : ResponseObject<ErrorMessage> {
	override fun toDomain(): ErrorMessage {
		return ErrorMessage(
			message = message,
			status = when (status) {
				401 -> HttpErrors.Unauthorized
				403 -> HttpErrors.Forbidden
				400 -> HttpErrors.BadRequest
				500 -> HttpErrors.ServerError
				409 -> HttpErrors.Conflict
				else -> HttpErrors.NotDefined
			}
		)
	}
}