package ir.aliranjbarzadeh.finances.presentation.profile.category

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.aliranjbarzadeh.finances.base.BaseAdapter
import ir.aliranjbarzadeh.finances.base.BaseHolder
import ir.aliranjbarzadeh.finances.base.interfaces.util.RecyclerViewCallback
import ir.aliranjbarzadeh.finances.data.models.Category
import ir.aliranjbarzadeh.finances.databinding.CategoryItemBinding

class CategoryListAdapter(private val callback: RecyclerViewCallback) : BaseAdapter<Category>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Category> {
		val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<Category>(binding) {
			override fun onBindUI(item: Category, position: Int) {
				binding.item = item

				binding.root.setOnClickListener { callback.onItemClick(item, bindingAdapterPosition, it) }

				binding.executePendingBindings()
			}
		}
	}
}