package ir.aliranjbarzadeh.finances.presentation.profile.card

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adivery.sdk.Adivery
import com.adivery.sdk.AdiveryListener
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseFragment
import ir.aliranjbarzadeh.finances.base.Configs
import ir.aliranjbarzadeh.finances.base.dispatchers.DispatchersProvider
import ir.aliranjbarzadeh.finances.base.extensions.observe
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.databinding.FragmentCardListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CardListFragment : BaseFragment<FragmentCardListBinding>(R.layout.fragment_card_list, R.string.cards, true) {
	@Inject
	lateinit var dispatchersProvider: DispatchersProvider

	private val viewModel: CardListViewModel by viewModels()

	private lateinit var cardAdapter: CardAdapter
	private var isInternetAvailable = false

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

		binding.btnAddCard.setOnClickListener {
			if (Adivery.isLoaded(Configs.Adivery.Placements.FULLSCREEN)) {
				Adivery.showAd(Configs.Adivery.Placements.FULLSCREEN)
			}
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

		CoroutineScope(dispatchersProvider.getMain()).launch {
			if (networkWatcher.isInternetAvailable()) {
				isInternetAvailable = true
				Adivery.prepareRewardedAd(requireContext(), Configs.Adivery.Placements.FULLSCREEN)
				logger.debug("internet is available", "INTERNET")
			}
		}
		Adivery.addPlacementListener(Configs.Adivery.Placements.FULLSCREEN, object : AdiveryListener() {
			override fun onRewardedAdClosed(placementId: String, isRewarded: Boolean) {
				logger.info("ad closed $isRewarded")
			}

			override fun log(placementId: String, message: String) {
				logger.info("$placementId -> $message", "ADIVERY")
			}
		})
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