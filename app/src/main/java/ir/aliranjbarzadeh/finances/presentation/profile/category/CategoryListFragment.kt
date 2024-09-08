package ir.aliranjbarzadeh.finances.presentation.profile.category

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseViewPagerChildFragment
import ir.aliranjbarzadeh.finances.base.extensions.observe
import ir.aliranjbarzadeh.finances.data.models.Category
import ir.aliranjbarzadeh.finances.databinding.FragmentCategoryListBinding
import ir.aliranjbarzadeh.finances.presentation.TransactionType

@AndroidEntryPoint
class CategoryListFragment(private val transactionType: TransactionType) : BaseViewPagerChildFragment<FragmentCategoryListBinding>(
	resId = R.layout.fragment_category_list
) {
	private val viewModel: CategoryListViewModel by viewModels()
	private val categoryAdapter = CategoryListAdapter(this)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
		viewModel.fetchCategories(transactionType)
	}

	private fun setupUI() {
		setupAdapter()
	}

	private fun setupAdapter() {
		binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
		binding.rvCategories.setHasFixedSize(true)
		binding.rvCategories.adapter = categoryAdapter

		try {
			if (binding.rvCategories.itemDecorationCount > 0) {
				binding.rvCategories.removeItemDecorationAt(0)
			}
		} catch (_: Exception) {
		} finally {
			val customDivider = ContextCompat.getDrawable(requireContext(), R.drawable.line_separator)
			val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
			customDivider?.let {
				dividerItemDecoration.setDrawable(it)
			}
			binding.rvCategories.addItemDecoration(dividerItemDecoration)
		}
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(categories(), ::initCategories)
			observe(isEmptyList(), ::initEmptyList)
		}
	}

	@SuppressLint("NotifyDataSetChanged")
	private fun initCategories(categories: List<Category>) {
		logger.info(categories, transactionType.name)

		categoryAdapter.mItems.clear()
		categoryAdapter.mItems.addAll(categories)
		categoryAdapter.notifyDataSetChanged()
	}
}