package ir.aliranjbarzadeh.finances.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.aliranjbarzadeh.finances.base.util.Logger
import ir.aliranjbarzadeh.finances.base.helpers.LanguageHelper
import ir.aliranjbarzadeh.finances.base.helpers.LocaleHelper
import javax.inject.Inject

open class BaseFragmentActivity<VDB : ViewDataBinding>(@LayoutRes private val resId: Int) : FragmentActivity() {
	lateinit var binding: VDB
	@Inject
	lateinit var logger: Logger

	override fun attachBaseContext(newBase: Context?) {
		newBase?.also {
			super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
			LocaleHelper.onAttach(
				context = newBase,
				language = LanguageHelper.getLanguage()
			)
		} ?: kotlin.run {
			super.attachBaseContext(newBase)
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, resId)
	}
}