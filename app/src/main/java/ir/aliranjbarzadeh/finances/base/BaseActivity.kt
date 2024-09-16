package ir.aliranjbarzadeh.finances.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.adivery.sdk.Adivery
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.aliranjbarzadeh.finances.base.util.Logger
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
//		LocaleHelper.setLocale(applicationContext, LanguageHelper.getLanguage())
		super.onCreate(savedInstanceState)
//		Adivery.setLoggingEnabled(true)
		Adivery.configure(application, Configs.Adivery.TOKEN)
		binding = DataBindingUtil.setContentView(this, resId)
	}

//	override fun onResume() {
//		LocaleHelper.setLocale(applicationContext, LanguageHelper.getLanguage())
//		super.onResume()
//	}
}