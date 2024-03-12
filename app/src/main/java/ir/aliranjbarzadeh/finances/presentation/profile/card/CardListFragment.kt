package ir.aliranjbarzadeh.finances.presentation.profile.card

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseFragment
import ir.aliranjbarzadeh.finances.databinding.FragmentCardListBinding

@AndroidEntryPoint
class CardListFragment : BaseFragment<FragmentCardListBinding>(R.layout.fragment_card_list, R.string.cards, true) {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}
}