package ir.aliranjbarzadeh.finances.data.sources.local

import androidx.room.TypeConverter
import ir.aliranjbarzadeh.finances.presentation.TransactionType
import java.util.Date

class Converters {
	@TypeConverter
	fun fromTimestamp(value: Long?): Date? {
		return value?.let { Date(it) }
	}

	@TypeConverter
	fun dateToTimestamp(date: Date?): Long? {
		return date?.time
	}

	@TypeConverter
	fun toTransactionType(value: String): TransactionType = enumValueOf(value.uppercase())

	@TypeConverter
	fun fromTransactionType(type: TransactionType): String = type.type
}