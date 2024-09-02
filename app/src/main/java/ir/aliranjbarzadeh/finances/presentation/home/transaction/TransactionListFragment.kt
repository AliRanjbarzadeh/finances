package ir.aliranjbarzadeh.finances.presentation.home.transaction

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseFragment
import ir.aliranjbarzadeh.finances.base.extensions.navTo
import ir.aliranjbarzadeh.finances.base.extensions.observe
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.data.models.Filter
import ir.aliranjbarzadeh.finances.data.models.Transaction
import ir.aliranjbarzadeh.finances.databinding.FragmentTransactionListBinding
import ir.aliranjbarzadeh.finances.presentation.DeepLinks
import ir.aliranjbarzadeh.finances.presentation.FragmentResults

@AndroidEntryPoint
class TransactionListFragment : BaseFragment<FragmentTransactionListBinding>(R.layout.fragment_transaction_list, R.string.transaction_list, false), MenuProvider {
	private val viewModel: TransactionListViewModel by viewModels()
	private val transactionAdapter = TransactionAdapter().apply {
		recyclerViewCallback = this@TransactionListFragment
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()

		setFragmentResultListener(FragmentResults.Transaction.stored, ::initFragmentResultListener)
		setFragmentResultListener(FragmentResults.Transaction.updated, ::initFragmentResultListener)
		setFragmentResultListener(FragmentResults.filters, ::initFragmentResultListener)
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

	override fun <T> onItemClick(item: T, position: Int, view: View) {
		item as Transaction
		logger.debug(item, "ITEM_CLICK")
		val action = TransactionListFragmentDirections.toDetail(
			transaction = item,
			adapterPosition = position
		)
		navTo(action)
	}

	override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
		menuInflater.inflate(R.menu.home_menu, menu)
	}

	override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
		return when (menuItem.itemId) {
			R.id.menu_filter_transactions -> {
				val action = TransactionListFragmentDirections.toFilter(viewModel.filters.toTypedArray())
				navTo(action)
				true
			}

			else -> false
		}
	}

	private fun setupUI() {
		toggleBackButton(false)

		val menuHost: MenuHost = requireActivity()
		menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

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
		navTo(DeepLinks.Card.add)
	}

	private fun addTransaction() {
		val action = TransactionListFragmentDirections.toAdd()
		navTo(action)
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
		when (requestKey) {
			FragmentResults.Transaction.stored -> initTransactionResult(bundle = bundle)

			FragmentResults.Transaction.updated -> initTransactionResult(bundle = bundle, isUpdate = true)

			FragmentResults.filters -> initFilterResult(bundle = bundle)
		}
	}

	private fun initTransactionResult(bundle: Bundle, isUpdate: Boolean = false) {
		val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			bundle.getParcelable(if (isUpdate) FragmentResults.updated else FragmentResults.stored, Transaction::class.java)
		} else {
			@Suppress("DEPRECATION")
			bundle.getParcelable(if (isUpdate) FragmentResults.updated else FragmentResults.stored)
		}

		// Get result from bundle of fragment listener and insert/update item
		result?.also { mTransaction ->
			if (isUpdate) {
				val adapterPosition = bundle.getInt(FragmentResults.adapterPosition, -1)
				if (adapterPosition >= 0) {
					transactionAdapter.mItems.set(adapterPosition, mTransaction)
					transactionAdapter.notifyItemChanged(adapterPosition)
				}
			} else {
				val isAdapterInitialized = transactionAdapter.mItems.isNotEmpty()
				if (!isAdapterInitialized) {
					viewModel.fetchTransactions()
				} else {
					transactionAdapter.mItems.add(0, mTransaction)
					transactionAdapter.notifyItemInserted(0)
				}
			}
		}
	}

	private fun initFilterResult(bundle: Bundle) {
		viewModel.filters.clear()
		val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			bundle.getParcelableArrayList(FragmentResults.filters, Filter::class.java)
		} else {
			@Suppress("DEPRECATION")
			bundle.getParcelableArrayList(FragmentResults.filters)
		}

		result?.also {
			viewModel.filters.addAll(it)
		}

		viewModel.fetchTransactions()
	}

	private fun initCards(cards: List<Card>) {
		if (cards.isEmpty()) {
			showAddCardDialog()
		} else {
			addTransaction()
		}
	}

	@SuppressLint("NotifyDataSetChanged")
	private fun initTransactions(transactions: List<Transaction>) {
		logger.debug(transactions, "TRANSACTIONS")
		transactionAdapter.mItems = transactions.toMutableList()
		if (binding.rvTransactions.adapter == null) {
			setupAdapter()
		} else {
			transactionAdapter.notifyDataSetChanged()
		}
	}

	private fun setupAdapter() {
		logger.debug("Transaction adapter setup", "TRANSACTION_ADAPTER")
		binding.rvTransactions.setHasFixedSize(true)
		binding.rvTransactions.layoutManager = LinearLayoutManager(requireContext())
		binding.rvTransactions.adapter = transactionAdapter
	}
}