package ir.aliranjbarzadeh.finances.presentation.home.filter

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseFragment
import ir.aliranjbarzadeh.finances.base.extensions.observe
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.data.models.Category
import ir.aliranjbarzadeh.finances.data.models.Filter
import ir.aliranjbarzadeh.finances.databinding.FragmentFilterBinding
import ir.aliranjbarzadeh.finances.presentation.FilterType
import ir.aliranjbarzadeh.finances.presentation.FragmentResults
import ir.aliranjbarzadeh.finances.presentation.TransactionType

@AndroidEntryPoint
class FilterFragment : BaseFragment<FragmentFilterBinding>(
	R.layout.fragment_filter,
	R.string.filter,
	true
) {
	private val args: FilterFragmentArgs by navArgs()
	private val viewModel: FilterViewModel by viewModels()

	private var mCategories: List<Category> = listOf()
	private var mCards: List<Card> = listOf()
	private var transactionType: TransactionType? = null
	private val mFilters = mutableListOf<Filter>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()

		args.filters?.also {
			mFilters.addAll(it)
		}

		mFilters.find { it.type == FilterType.TRANSACTION_TYPE }?.also {
			transactionType = TransactionType.from(it.value)
			viewModel.fetchCategories(transactionType!!)
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	override fun getMainView(): ViewGroup = binding.mainView

	private fun setupUI() {
		when (transactionType) {
			TransactionType.DEPOSIT -> binding.tbTransactionType.check(R.id.btn_deposit)
			TransactionType.WITHDRAW -> binding.tbTransactionType.check(R.id.btn_withdraw)
			else -> binding.tbTransactionType.clearChecked()
		}

		binding.tbTransactionType.addOnButtonCheckedListener { tbBtn, checkedId, isChecked ->
			if (isChecked) {
				transactionType = if (checkedId == R.id.btn_deposit) {
					TransactionType.DEPOSIT
				} else {
					TransactionType.WITHDRAW
				}
				viewModel.fetchCategories(transactionType!!)
			} else {
				if (tbBtn.checkedButtonId == -1) {
					transactionType = null
					resetCategories()
				}
			}
		}

		binding.actCategories.setOnItemClickListener { _, _, position, _ ->
			unSelectCategories(mCategories[position].id)
		}

		binding.actCards.setOnItemClickListener { _, _, position, _ ->
			unSelectCards(mCards[position].id)
		}

		binding.btnFilter.setOnClickListener {
			//Clear all filters
			mFilters.clear()

			//Transaction type filter
			transactionType?.also { mTransactionType ->
				val transactionTypeFilter = mFilters.find { mFilter -> mFilter.type == FilterType.TRANSACTION_TYPE }
				when (transactionTypeFilter) {
					null -> {
						mFilters.add(
							Filter(
								type = FilterType.TRANSACTION_TYPE,
								value = mTransactionType.type
							)
						)
					}

					else -> {
						transactionTypeFilter.value = mTransactionType.type
					}
				}
			}

			//Category filter
			mCategories.find { it.isSelected }?.also { mCategory ->
				mFilters.add(
					Filter(
						type = FilterType.CATEGORY,
						value = mCategory.id.toString()
					)
				)
			}

			//Card filter
			mCards.find { it.isSelected }?.also { mCard ->
				mFilters.add(
					Filter(
						type = FilterType.CARD,
						value = mCard.id.toString()
					)
				)
			}

			setFragmentResult(FragmentResults.filters, bundleOf(FragmentResults.filters to mFilters))
			back()
		}
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(categories(), ::initCategories)
			observe(cards(), ::initCards)
		}
	}

	private fun initCategories(categories: List<Category>) {
		resetCategories()
		mCategories = categories
		val items = categories.map { it.name }.toTypedArray()
		binding.actCategories.setSimpleItems(items)

		val selected = args.filters?.find { it.type == FilterType.CATEGORY }?.value?.toLong() ?: 0L
		unSelectCategories(selected)
	}

	private fun resetCategories() {
		binding.actCategories.clearListSelection()
		binding.actCategories.setText("", false)
		binding.actCategories.clearFocus()
		binding.actCategories.setSimpleItems(arrayOf())
		unSelectCategories()
	}

	private fun unSelectCategories(selected: Long = 0) {
		mCategories.forEach { it.isSelected = false }

		if (selected > 0) {
			mCategories.first { it.id == selected }.also { mCategory ->
				mCategory.isSelected = true
				binding.actCategories.setText(mCategory.name, false)
			}
		}
	}

	private fun initCards(cards: List<Card>) {
		val items = cards.map { it.name }.toTypedArray()
		binding.actCards.setSimpleItems(items)
		mCards = cards

		val selected = args.filters?.find { it.type == FilterType.CARD }?.value?.toLong() ?: 0L
		unSelectCards(selected)
	}

	private fun unSelectCards(selected: Long = 0) {
		mCards.forEach { it.isSelected = false }
		if (selected > 0) {
			mCards.first { it.id == selected }.also { mCard ->
				mCard.isSelected = true
				binding.actCards.setText(mCard.name, false)
			}
		}
	}
}