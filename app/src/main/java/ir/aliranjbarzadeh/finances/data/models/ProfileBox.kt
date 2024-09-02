package ir.aliranjbarzadeh.finances.data.models

import androidx.annotation.DrawableRes

data class ProfileBox(
	var title: String,
	var items: MutableList<Item>,
) {
	data class Item(
		var type: String,
		var title: String,
		@DrawableRes
		var icon: Int,
	)
}
