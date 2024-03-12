package ir.aliranjbarzadeh.finances.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import gun0912.tedkeyboardobserver.TedKeyboardObserver
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.di.Logger
import ir.aliranjbarzadeh.finances.databinding.LoadingBinding
import javax.inject.Inject

abstract class BaseFragment<VDB : ViewDataBinding>(
	@LayoutRes private val resId: Int,
	@StringRes private val titleResId: Int,
	private val isShowBackButton: Boolean,
) : Fragment() {
	lateinit var binding: VDB
	private lateinit var loadingBinding: LoadingBinding

	@Inject
	lateinit var logger: Logger

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = DataBindingUtil.inflate(inflater, resId, container, false)

		//Set title
		requireActivity().setTitle(titleResId)

		//set background of fragment
		binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.md_theme_background))

		//loading
		loadingBinding = LoadingBinding.inflate(layoutInflater, null, false)

		toggleBackButton(isShowBackButton)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		TedKeyboardObserver(requireActivity()).listen { isShow ->
			keyboardState(isShow)
		}
	}

	protected open fun keyboardState(isShow: Boolean) {}

	fun hideKeyboard() {
		val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
		view?.also {
			imm.hideSoftInputFromWindow(it.rootView.windowToken, 0)
		}
	}

	fun toggleBackButton(isShow: Boolean) {
		(requireActivity() as AppCompatActivity).supportActionBar?.also {
			it.setDisplayHomeAsUpEnabled(isShow)
			it.setDisplayShowHomeEnabled(isShow)
		}
	}

	fun scrollToTop(nestedScrollView: NestedScrollView) {
		nestedScrollView.fullScroll(View.FOCUS_UP)
		nestedScrollView.scrollTo(0, 0)
	}

	fun toggleLoading(isShow: Boolean, parentView: ViewGroup) {
		if (isShow) {
			parentView.addView(loadingBinding.root)
		} else {
			try {
				parentView.removeView(loadingBinding.root)
			} catch (_: Exception) {
			}
		}
	}

	fun back() {
		findNavController().popBackStack()
	}

	fun navToAction() {
	}

	fun navToDeeplink(deepLink: String) {
		val request = NavDeepLinkRequest.Builder
			.fromUri(deepLink.toUri())
			.build()
		findNavController().navigate(request)
	}
}