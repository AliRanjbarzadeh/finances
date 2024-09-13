package ir.aliranjbarzadeh.finances.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.aliranjbarzadeh.finances.base.util.Logger
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment<VDB : ViewDataBinding>(
	@LayoutRes private val resId: Int,
) : BottomSheetDialogFragment() {

	@Inject
	lateinit var logger: Logger

	lateinit var binding: VDB

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = DataBindingUtil.inflate(inflater, resId, container, false)

		return binding.root
	}
}