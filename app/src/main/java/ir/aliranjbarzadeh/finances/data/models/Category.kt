package ir.aliranjbarzadeh.finances.data.models

import android.os.Parcelable
import ir.aliranjbarzadeh.finances.presentation.TransactionType
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Category(
	val id: Long = 0,
	var name: String,
	var type: TransactionType,
	var isDeletable: Boolean = true,
	var priority: Int = 0,
	var createdAt: Date,
	var updatedAt: Date,
	var isSelected: Boolean = false,
) : Parcelable {
	override fun toString(): String {
		return "Category(id=$id, name='$name')"
	}
}
