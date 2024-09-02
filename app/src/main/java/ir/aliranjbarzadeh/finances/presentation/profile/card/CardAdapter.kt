package ir.aliranjbarzadeh.finances.presentation.profile.card

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseAdapter
import ir.aliranjbarzadeh.finances.base.BaseHolder
import ir.aliranjbarzadeh.finances.base.extensions.priceFormat
import ir.aliranjbarzadeh.finances.base.interfaces.util.RecyclerViewCallback
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.databinding.CardItemBinding

class CardAdapter(private val mContext: Context, private val callback: RecyclerViewCallback) : BaseAdapter<Card>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Card> {
		val binding = CardItemBinding.inflate(LayoutInflater.from(mContext), parent, false)

		return object : BaseHolder<Card>(binding) {
			override fun onBindUI(item: Card, position: Int) {
				binding.item = item

				binding.txtEntryBalance.setText(mContext.getString(R.string.balance_amount, item.currentBalance.priceFormat(mContext.getString(R.string.price_sign))))
				binding.txtDeposit.setText(mContext.getString(R.string.deposits_amount, item.deposit.priceFormat(mContext.getString(R.string.price_sign))))
				binding.txtWithdraw.setText(mContext.getString(R.string.withdraws_amount, item.withdraw.priceFormat(mContext.getString(R.string.price_sign))))
				binding.txtCreatedAt.setText(mContext.getString(R.string.created_at, item.dateFormatted))

				binding.executePendingBindings()
			}
		}
	}
}