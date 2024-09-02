package ir.aliranjbarzadeh.finances.presentation.home.transaction

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseBottomSheetDialogFragment
import ir.aliranjbarzadeh.finances.base.extensions.navTo
import ir.aliranjbarzadeh.finances.databinding.BsTransactionDetailBinding

@AndroidEntryPoint
class TransactionDetailBottomSheet : BaseBottomSheetDialogFragment<BsTransactionDetailBinding>(R.layout.bs_transaction_detail) {

	private val args: TransactionDetailBottomSheetArgs by navArgs()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		logger.debug(args.transaction, "ARGS_TRANS")

		binding.item = args.transaction

		binding.btnEdit.setOnClickListener {
			val action = TransactionDetailBottomSheetDirections.toTransactionEdit(
				transaction = args.transaction,
				adapterPosition = args.adapterPosition
			)
			navTo(action)
		}
	}
}