package ir.aliranjbarzadeh.finances.presentation.home.transaction.add

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseFragment
import ir.aliranjbarzadeh.finances.base.extensions.numberFormat
import ir.aliranjbarzadeh.finances.base.extensions.observe
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.data.models.Category
import ir.aliranjbarzadeh.finances.data.models.Transaction
import ir.aliranjbarzadeh.finances.databinding.FragmentTransactionAddBinding
import ir.aliranjbarzadeh.finances.presentation.FragmentResults
import ir.aliranjbarzadeh.finances.presentation.TransactionType

@AndroidEntryPoint
class TransactionAddFragment : BaseFragment<FragmentTransactionAddBinding>(R.layout.fragment_transaction_add, R.string.transaction_add, true) {

	private val viewModel: TransactionAddViewModel by viewModels()

	private lateinit var mCategories: List<Category>
	private lateinit var mCards: List<Card>
	private val transaction: Transaction = Transaction.emptyObject()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.btnAddTransaction.setOnClickListener {
			viewModel.storeItem(transaction)
		}

		binding.tbTransactionType.addOnButtonCheckedListener { _, checkedId, isChecked ->
			if (isChecked) {
				val transactionType = if (checkedId == R.id.btn_deposit) {
					TransactionType.DEPOSIT
				} else {
					TransactionType.WITHDRAW
				}

				viewModel.fetchCategories(transactionType)
				transaction.categoryId = 0
				transaction.type = transactionType
			}
		}

		binding.actCategories.setOnItemClickListener { _, _, position, _ ->
			logger.debug(position, "CATEGORY")
			transaction.categoryId = mCategories[position].id
		}

		binding.etAmount.numberFormat { price ->
			transaction.price = price
		}

		binding.etDescription.doAfterTextChanged { text: Editable? ->
			transaction.description = text.toString()
		}
	}

	override fun getMainView(): ViewGroup = binding.mainView

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(categories(), ::initCategories)
			observe(cards(), ::initCards)
			observe(error(), ::initError)
			observe(store(), ::initStore)
		}
	}

	private fun initCategories(categories: List<Category>) {
		binding.actCategories.clearListSelection()
		binding.actCategories.setText("", false)
		binding.actCategories.clearFocus()
		mCategories = categories
		val items = categories.map { it.name }.toTypedArray()
		binding.actCategories.setSimpleItems(items)
	}

	private fun initCards(cards: List<Card>) {
		val items = cards.map { it.name }.toTypedArray()
		binding.actCards.setSimpleItems(items)
		mCards = cards

		if (cards.size == 1) {
			cards.first().also { card ->
				binding.actCards.setText(card.name, false)
				transaction.apply {
					cardId = card.id
					bankId = card.bankId
				}
			}
		}
	}

	private fun initError(@StringRes messageResId: Int) {
		Toast.makeText(requireContext(), getString(messageResId), Toast.LENGTH_SHORT).show()
	}

	private fun initStore(transactionId: Long) {
		transaction.id = transactionId
		transaction.category = mCategories.find { it.id == transaction.categoryId }
		transaction.card = mCards.find { it.id == transaction.cardId }
		setFragmentResult(FragmentResults.Transaction.stored, bundleOf(FragmentResults.stored to transaction))
		back()
	}
}