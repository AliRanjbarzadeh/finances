package ir.aliranjbarzadeh.finances.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import ir.aliranjbarzadeh.finances.base.BaseAdapter
import ir.aliranjbarzadeh.finances.base.BaseHolder
import ir.aliranjbarzadeh.finances.base.interfaces.util.RecyclerViewCallback
import ir.aliranjbarzadeh.finances.data.models.ProfileBox
import ir.aliranjbarzadeh.finances.databinding.TemplateProfileBoxBinding

class ProfileBoxAdapter(private val callback: RecyclerViewCallback, private val viewPool: RecycledViewPool) : BaseAdapter<ProfileBox>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ProfileBox> {
		val binding = TemplateProfileBoxBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return object : BaseHolder<ProfileBox>(binding) {
			override fun onBindUI(item: ProfileBox, position: Int) {
				binding.item = item

				binding.rvBoxItems.setHasFixedSize(false)
				binding.rvBoxItems.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
				val adapter = ProfileBoxItemAdapter(callback)
				adapter.mItems = item.items
				binding.rvBoxItems.adapter = adapter
				binding.rvBoxItems.setRecycledViewPool(viewPool)

				binding.executePendingBindings()
			}
		}
	}
}