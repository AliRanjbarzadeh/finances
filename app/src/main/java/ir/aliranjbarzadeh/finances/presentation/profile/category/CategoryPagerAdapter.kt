package ir.aliranjbarzadeh.finances.presentation.profile.category

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ir.aliranjbarzadeh.finances.presentation.TransactionType

class CategoryPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
	override fun getItemCount(): Int = 2

	override fun createFragment(position: Int): Fragment {
		return CategoryListFragment.newInstance(
			if (position == 0) {
				TransactionType.DEPOSIT
			} else {
				TransactionType.WITHDRAW
			}
		)
	}
}