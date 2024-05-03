package ir.aliranjbarzadeh.finances.base.interfaces.util

import android.view.View

interface RecyclerViewCallback {
	fun <T> onItemClick(item: T, position: Int, view: View) {}
}