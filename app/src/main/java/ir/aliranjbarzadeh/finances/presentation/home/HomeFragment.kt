package ir.aliranjbarzadeh.finances.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseFragment
import ir.aliranjbarzadeh.finances.base.extensions.observe
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.data.models.Transaction
import ir.aliranjbarzadeh.finances.databinding.FragmentHomeBinding
import ir.aliranjbarzadeh.finances.presentation.DeepLinks

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home, R.string.home_menu, false) {

	private val viewModel: HomeViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		toggleBackButton(false)

		if (!viewModel.isFirstRun) {
			viewModel.updateView()
		}

		binding.btnAddTransaction.setOnClickListener {
			viewModel.fetchCards()
		}
	}

	private fun goToAddCard() {
		navToDeeplink(DeepLinks.Card.add)
	}

	private fun addTransaction() {
		Toast.makeText(requireContext(), "go to add transaction", Toast.LENGTH_SHORT).show()
	}

	private fun showAddCardDialog() {
		MaterialAlertDialogBuilder(requireContext())
			.setCancelable(false)
			.setTitle(R.string.add_card)
			.setMessage(R.string.need_card_description)
			.setPositiveButton(R.string.add_card) { dialog, _ ->
				dialog.dismiss()
				goToAddCard()
			}
			.show()
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(cards(), ::initCards)
			observe(transactions(), ::initTransactions)
			observe(isEmptyList(), ::initEmptyList)
		}
	}

	private fun initLoading(isLoading: Boolean) {
		toggleLoading(isLoading, binding.mainView)
	}

	private fun initCards(cards: List<Card>) {
		if (cards.isEmpty()) {
			showAddCardDialog()
		} else {
			addTransaction()
		}
	}

	private fun initTransactions(transactions: List<Transaction>) {
		logger.debug(transactions, "TRANSACTIONS")
	}

	private fun initEmptyList(isEmptyList: Boolean) {
		binding.rvTransactions.isVisible = !isEmptyList
		binding.emptyList.root.isVisible = isEmptyList
	}
}