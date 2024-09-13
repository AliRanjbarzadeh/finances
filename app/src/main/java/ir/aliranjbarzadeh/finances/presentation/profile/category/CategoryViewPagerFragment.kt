package ir.aliranjbarzadeh.finances.presentation.profile.category

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseFragment
import ir.aliranjbarzadeh.finances.databinding.FragmentCategoryViewPagerBinding
import ir.aliranjbarzadeh.finances.databinding.TemplateCategoryTablayoutBinding

@AndroidEntryPoint
class CategoryViewPagerFragment : BaseFragment<FragmentCategoryViewPagerBinding>(
	resId = R.layout.fragment_category_view_pager,
	titleResId = R.string.categories,
	isShowBackButton = true
) {

	private lateinit var categoryPagerAdapter: CategoryPagerAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		categoryPagerAdapter = CategoryPagerAdapter(this)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}


	private fun setupUI() {
		if (binding.vpCategories.adapter == null) {
			binding.vpCategories.adapter = categoryPagerAdapter
		}
		binding.vpCategories.offscreenPageLimit = 2

		TabLayoutMediator(binding.tlCategories, binding.vpCategories) { tab: TabLayout.Tab, position: Int ->
			tab.customView = getTabCustomView(position)
		}.attach()
	}

	private fun getTabCustomView(position: Int): View {
		val tabView = TemplateCategoryTablayoutBinding.inflate(layoutInflater, null, false)
		var colorRes = R.color.deposit
		when (position) {
			0 -> {
				tabView.txtTitle.setText(R.string.deposit)
				tabView.imgIcon.setImageResource(R.drawable.ic_keyboard_arrow_up)
			}

			1 -> {
				colorRes = R.color.withdraw
				tabView.txtTitle.setText(R.string.withdraw)
				tabView.imgIcon.setImageResource(R.drawable.ic_keyboard_arrow_down)
			}
		}

		tabView.txtTitle.setTextColor(ContextCompat.getColor(requireContext(), colorRes))
		tabView.imgIcon.imageTintList = ContextCompat.getColorStateList(requireContext(), colorRes)


		return tabView.root
	}
}