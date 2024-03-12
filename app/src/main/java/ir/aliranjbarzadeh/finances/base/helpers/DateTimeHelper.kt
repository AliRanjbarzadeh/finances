package ir.aliranjbarzadeh.finances.base.helpers

import java.util.Calendar
import java.util.Date
import java.util.TimeZone

object DateTimeHelper {

	fun currentDate(): Date {
		return Calendar.getInstance().time
	}

	fun currentDateUTC(): Date {
		val utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
		utcCalendar.time = currentDate()

		return utcCalendar.time
	}
}