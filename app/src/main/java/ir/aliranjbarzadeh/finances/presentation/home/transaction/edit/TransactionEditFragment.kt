package ir.aliranjbarzadeh.finances.presentation.home.transaction.edit

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
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseFragment
import ir.aliranjbarzadeh.finances.base.extensions.numberFormat
import ir.aliranjbarzadeh.finances.base.extensions.observe
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.data.models.Category
import ir.aliranjbarzadeh.finances.data.models.Transaction
import ir.aliranjbarzadeh.finances.databinding.FragmentTransactionEditBinding
import ir.aliranjbarzadeh.finances.presentation.FragmentResults
import ir.aliranjbarzadeh.finances.presentation.TransactionType

@AndroidEntryPoint
class TransactionEditFragment : BaseFragment<FragmentTransactionEditBinding>(R.layout.fragment_transaction_edit, R.string.transaction_edit, true) {

	private val args: TransactionEditFragmentArgs by navArgs()
	private val viewModel: TransactionEditViewModel by viewModels()

	private lateinit var mCategories: List<Category>
	private lateinit var mCards: List<Card>
	private lateinit var transaction: Transaction

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		//set transaction from args
		transaction = args.transaction.copy()

		setupObservers()

		viewModel.fetchCategories(transaction.type)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.btnEditTransaction.setOnClickListener {
			viewModel.updateItem(transaction)
		}

		binding.tbTransactionType.check(if (transaction.type == TransactionType.DEPOSIT) R.id.btn_deposit else R.id.btn_withdraw)
		binding.tbTransactionType.addOnButtonCheckedListener { _, checkedId, isChecked ->
			if (isChecked) {
				val transactionType = if (checkedId == R.id.btn_deposit) {
					TransactionType.DEPOSIT
				} else {
					TransactionType.WITHDRAW
				}

				viewModel.fetchCategories(transactionType)
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

		//set binding data
		binding.item = transaction
	}

	override fun getMainView(): ViewGroup = binding.mainView

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(categories(), ::initCategories)
			observe(cards(), ::initCards)
			observe(error(), ::initError)
			observe(update(), ::initUpdate)
		}
	}

	private fun initCategories(categories: List<Category>) {
		binding.actCategories.clearListSelection()
		binding.actCategories.setText("", false)
		binding.actCategories.clearFocus()
		mCategories = categories
		val items = categories.map { it.name }.toTypedArray()
		binding.actCategories.setSimpleItems(items)

		categories.forEach { category ->
			if (category.id == transaction.categoryId) {
				binding.actCategories.setText(category.name, false)
				//break loop
				return@forEach
			}
		}
	}

	private fun initCards(cards: List<Card>) {
		val items = cards.map { it.name }.toTypedArray()
		binding.actCards.setSimpleItems(items)
		mCards = cards

		cards.forEach { card ->
			if (card.id == transaction.cardId) {
				binding.actCards.setText(card.name, false)
				//break loop
				return@forEach
			}
		}
	}

	private fun initError(@StringRes messageResId: Int) {
		Toast.makeText(requireContext(), getString(messageResId), Toast.LENGTH_SHORT).show()
	}

	private fun initUpdate(rowsAffected: Int) {
		setFragmentResult(
			FragmentResults.Transaction.updated, bundleOf(
				FragmentResults.updated to transaction,
				FragmentResults.adapterPosition to args.adapterPosition
			)
		)
		back()
	}
}