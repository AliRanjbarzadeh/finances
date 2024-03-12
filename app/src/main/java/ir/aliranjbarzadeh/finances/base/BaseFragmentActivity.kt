package ir.aliranjbarzadeh.finances.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.aliranjbarzadeh.finances.base.di.Logger
import ir.aliranjbarzadeh.finances.base.extensions.loadFromSp
import ir.aliranjbarzadeh.finances.base.helpers.LanguageHelper
import ir.aliranjbarzadeh.finances.base.helpers.LocalHelper
import javax.inject.Inject

open class BaseFragmentActivity<VDB : ViewDataBinding>(@LayoutRes private val resId: Int) : FragmentActivity() {
	lateinit var binding: VDB
	@Inject
	lateinit var logger: Logger

	override fun attachBaseContext(newBase: Context?) {
		newBase?.also {
			super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
			LocalHelper.onAttach(
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