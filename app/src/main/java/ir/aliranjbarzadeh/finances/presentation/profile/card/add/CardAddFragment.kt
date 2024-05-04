package ir.aliranjbarzadeh.finances.presentation.profile.card.add

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseFragment
import ir.aliranjbarzadeh.finances.base.extensions.numberFormat
import ir.aliranjbarzadeh.finances.base.extensions.observe
import ir.aliranjbarzadeh.finances.base.extensions.toEnglish
import ir.aliranjbarzadeh.finances.data.models.Bank
import ir.aliranjbarzadeh.finances.data.models.Card
import ir.aliranjbarzadeh.finances.databinding.FragmentCardAddBinding

@AndroidEntryPoint
class CardAddFragment : BaseFragment<FragmentCardAddBinding>(R.layout.fragment_card_add, R.string.add_card, true) {

	private val viewModel: CardAddViewModel by viewModels()

	private lateinit var mBanks: List<Bank>
	private val card = Card.emptyObject(true)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.actBanks.setOnItemClickListener { _, _, position, _ ->
			logger.debug(position, "BANK_SELECTION")
			card.bankId = mBanks[position].id
		}

		binding.etCardName.doAfterTextChanged { text: Editable? ->
			card.name = text.toString().toEnglish()
		}

		binding.etCardBalance.numberFormat { number: Long ->
			card.balance = number
		}

		binding.btnAddCard.setOnClickListener {
			hideKeyboard()
			viewModel.storeItem(card)
		}
	}

	override fun keyboardState(isShow: Boolean) {
		if (!isShow) {
			binding.actBanks.clearFocus()
			binding.etCardName.clearFocus()
			binding.etCardBalance.clearFocus()
		}
	}

	override fun getMainView(): ViewGroup = binding.mainView

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
			observe(banks(), ::initBanks)
			observe(store(), ::initStore)
			observe(error(), ::initError)
		}
	}

	private fun initBanks(banks: List<Bank>) {
		logger.debug(banks, "BANKS")
		mBanks = banks
		val items = banks.map { it.name }.toTypedArray()
		binding.actBanks.setSimpleItems(items)
	}

	private fun initStore(cardId: Long) {
		back()
	}

	private fun initError(@StringRes messageResId: Int) {
		Toast.makeText(requireContext(), getString(messageResId), Toast.LENGTH_SHORT).show()
	}

}