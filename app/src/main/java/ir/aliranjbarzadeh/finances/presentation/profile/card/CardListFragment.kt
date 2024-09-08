package ir.aliranjbarzadeh.finances.presentation.profile.card

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseFragment
import ir.aliranjbarzadeh.finances.base.extensions.observe
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.databinding.FragmentCardListBinding

@AndroidEntryPoint
class CardListFragment : BaseFragment<FragmentCardListBinding>(R.layout.fragment_card_list, R.string.cards, true) {
	private val viewModel: CardListViewModel by viewModels()

	lateinit var cardAdapter: CardAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		cardAdapter = CardAdapter(mContext = requireContext(), callback = this)

		setupObservers()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	private fun setupUI() {
		if (cardAdapter.mItems.isNotEmpty()) {
			setupAdapter()
		}
	}

	private fun setupAdapter() {
		binding.rvCards.setHasFixedSize(true)
		binding.rvCards.layoutManager = LinearLayoutManager(requireContext())
		binding.rvCards.adapter = cardAdapter
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(cards(), ::initCards)
			observe(isEmptyList(), ::initEmptyList)
		}
	}

	@SuppressLint("NotifyDataSetChanged")
	private fun initCards(cards: List<Card>) {
		logger.info(cards, "CARDS")

		cardAdapter.mItems = cards.toMutableList()
		if (binding.rvCards.adapter == null) {
			setupAdapter()
		} else {
			cardAdapter.notifyDataSetChanged()
		}
	}
}