package ir.aliranjbarzadeh.finances.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Contact(
	val id: Long = 0,
	var name: String,
	var createdAt: Date,
	var updatedAt: Date,
) : Parcelable
