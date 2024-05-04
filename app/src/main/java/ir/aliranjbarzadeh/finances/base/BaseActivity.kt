package ir.aliranjbarzadeh.finances.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.aliranjbarzadeh.finances.base.di.Logger
import ir.aliranjbarzadeh.finances.base.helpers.LanguageHelper
import ir.aliranjbarzadeh.finances.base.helpers.LocaleHelper
import javax.inject.Inject

abstract class BaseActivity<VDB : ViewDataBinding>(
	@LayoutRes private val resId: Int,
) : AppCompatActivity() {
	lateinit var binding: VDB

	@Inject
	lateinit var logger: Logger

	override fun attachBaseContext(newBase: Context) {
		LocaleHelper.onAttach(
			context = newBase,
			language = LanguageHelper.getLanguage()
		)
		super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, resId)
	}
}