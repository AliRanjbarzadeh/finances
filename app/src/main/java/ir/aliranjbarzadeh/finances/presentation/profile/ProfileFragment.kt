package ir.aliranjbarzadeh.finances.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseFragment
import ir.aliranjbarzadeh.finances.base.extensions.navTo
import ir.aliranjbarzadeh.finances.data.models.ProfileBox
import ir.aliranjbarzadeh.finances.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile, R.string.profile_menu, false) {

	private val viewPool = RecycledViewPool()
	private lateinit var profileBoxAdapter: ProfileBoxAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		profileBoxAdapter = ProfileBoxAdapter(this, viewPool)
		initializeMenuItems()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupUI()
	}

	override fun <T> onItemClick(item: T, position: Int, view: View) {
		item as ProfileBox.Item

		when (item.type) {
			"card" -> {
				val action = ProfileFragmentDirections.profileToCardList()
				navTo(action)
			}
		}
	}

	private fun initializeMenuItems() {
		val menuItems = mutableListOf(
			ProfileBox(
				title = getString(R.string.transaction_list),
				items = mutableListOf(
					ProfileBox.Item(
						type = "card",
						title = getString(R.string.my_cards),
						R.drawable.ic_credit_card
					),
					ProfileBox.Item(
						type = "category",
						title = getString(R.string.categories),
						R.drawable.ic_category
					)
				)
			)
		)

		profileBoxAdapter.mItems = menuItems
	}

	private fun setupUI() {
		toggleBackButton(false)

		binding.rvBoxes.setHasFixedSize(false)
		binding.rvBoxes.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		binding.rvBoxes.adapter = profileBoxAdapter
		binding.rvBoxes.setRecycledViewPool(viewPool)
		binding.rvBoxes.setItemViewCacheSize(profileBoxAdapter.mItems.size)
	}
}