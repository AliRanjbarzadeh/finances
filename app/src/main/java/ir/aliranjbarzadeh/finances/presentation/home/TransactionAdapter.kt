package ir.aliranjbarzadeh.finances.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseAdapter
import ir.aliranjbarzadeh.finances.base.BaseHolder
import ir.aliranjbarzadeh.finances.base.helpers.NumberHelper
import ir.aliranjbarzadeh.finances.base.interfaces.util.RecyclerViewCallback
import ir.aliranjbarzadeh.finances.data.models.Transaction
import ir.aliranjbarzadeh.finances.databinding.TransactionItemBinding
import ir.aliranjbarzadeh.finances.presentation.TransactionType

class TransactionAdapter : BaseAdapter<Transaction>() {

	lateinit var recyclerViewCallback: RecyclerViewCallback

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Transaction> {
		val binding = TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		val mContext = parent.context
		return object : BaseHolder<Transaction>(binding) {
			override fun onBindUI(item: Transaction, position: Int) {
				binding.item = item

				binding.tPrice.text = NumberHelper.format(item.price, ContextCompat.getString(mContext, R.string.price_sign))

				val bgColor = if (item.type == TransactionType.DEPOSIT) {
					ContextCompat.getColor(mContext, R.color.deposit)
				} else {
					ContextCompat.getColor(mContext, R.color.withdraw)
				}
				binding.vType.setBackgroundColor(bgColor)

				binding.root.setOnClickListener { recyclerViewCallback.onItemClick(item, position, it) }

				binding.executePendingBindings()
			}
		}
	}
}