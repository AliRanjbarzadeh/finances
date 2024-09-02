package ir.aliranjbarzadeh.finances.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import ir.aliranjbarzadeh.finances.base.BaseAdapter
import ir.aliranjbarzadeh.finances.base.BaseHolder
import ir.aliranjbarzadeh.finances.base.interfaces.util.RecyclerViewCallback
import ir.aliranjbarzadeh.finances.data.models.ProfileBox
import ir.aliranjbarzadeh.finances.databinding.TemplateProfileItemBinding

class ProfileBoxItemAdapter(private val callback: RecyclerViewCallback) : BaseAdapter<ProfileBox.Item>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ProfileBox.Item> {
		val binding = TemplateProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return object : BaseHolder<ProfileBox.Item>(binding) {
			override fun onBindUI(item: ProfileBox.Item, position: Int) {
				binding.item = item

				binding.imgIcon.setImageResource(item.icon)

				binding.root.setOnClickListener { callback.onItemClick(item, bindingAdapterPosition, it) }

				binding.executePendingBindings()
			}
		}
	}
}