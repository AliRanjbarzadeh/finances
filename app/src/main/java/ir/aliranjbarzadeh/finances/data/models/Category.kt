package ir.aliranjbarzadeh.finances.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Category(
	val id: Long = 0,
	var name: String,
	var type: String, // income outcome
	var isDeletable: Boolean = true,
	var createdAt: Date,
	var updatedAt: Date,
) : Parcelable {
	override fun toString(): String {
		return "Category(id=$id, name='$name')"
	}
}
