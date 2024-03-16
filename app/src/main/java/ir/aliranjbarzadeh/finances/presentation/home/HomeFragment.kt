package ir.aliranjbarzadeh.finances.presentation.home

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseFragment
import ir.aliranjbarzadeh.finances.base.extensions.observe
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.data.models.Transaction
import ir.aliranjbarzadeh.finances.databinding.FragmentHomeBinding
import ir.aliranjbarzadeh.finances.presentation.DeepLinks
import ir.aliranjbarzadeh.finances.presentation.FragmentResults

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home, R.string.home_menu, false) {

	private val viewModel: HomeViewModel by viewModels()
	private val transactionAdapter = TransactionAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()

		setFragmentResultListener(FragmentResults.Transaction.stored, ::initFragmentResultListener)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	override fun getMainView(): ViewGroup {
		return binding.mainView
	}

	override fun initEmptyList(isEmptyList: Boolean) {
		super.initEmptyList(isEmptyList)
		binding.rvTransactions.isVisible = !isEmptyList
	}

	private fun setupUI() {
		toggleBackButton(false)

		if (!viewModel.isFirstRun) {
			viewModel.updateView()
		}

		binding.btnAddTransaction.setOnClickListener {
			viewModel.fetchCards()
		}

		if (transactionAdapter.mItems.isNotEmpty()) {
			setupAdapter()
		}
	}

	private fun goToAddCard() {
		navToDeeplink(DeepLinks.Card.add)
	}

	private fun addTransaction() {
		val action = HomeFragmentDirections.homeToTransactionAdd()
		navToAction(action)
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

	private fun initFragmentResultListener(requestKey: String, bundle: Bundle) {
		val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			bundle.getParcelable(FragmentResults.stored, Transaction::class.java)
		} else {
			@Suppress("DEPRECATION")
			bundle.getParcelable(FragmentResults.stored)
		}
		logger.debug(result, "FRAGMENT_RESULT")

		result?.also {
			val isAdapterInitialized = transactionAdapter.mItems.isNotEmpty()
			if (!isAdapterInitialized) {
				viewModel.fetchTransactions()
			} else {
				transactionAdapter.mItems.add(0, result)
				transactionAdapter.notifyItemInserted(0)
			}
		}
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
		transactionAdapter.mItems = transactions.toMutableList()
		setupAdapter()
	}

	private fun setupAdapter() {
		logger.debug("Transaction adapter setup", "TRANSACTION_ADAPTER")
		binding.rvTransactions.setHasFixedSize(true)
		binding.rvTransactions.layoutManager = LinearLayoutManager(requireContext())
		binding.rvTransactions.adapter = transactionAdapter
	}
}