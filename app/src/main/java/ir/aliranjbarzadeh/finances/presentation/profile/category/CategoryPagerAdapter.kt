package ir.aliranjbarzadeh.finances.presentation.profile.category

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import ir.aliranjbarzadeh.finances.presentation.TransactionType

class CategoryPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
	override fun getItemCount(): Int = 2

	override fun onBindViewHolder(holder: FragmentViewHolder, position: Int, payloads: MutableList<Any>) {
		super.onBindViewHolder(holder, position, payloads)
	}

	override fun createFragment(position: Int): Fragment {
		return CategoryListFragment(
			if (position == 0) {
				TransactionType.DEPOSIT
			} else {
				TransactionType.WITHDRAW
			}
		)
	}
}