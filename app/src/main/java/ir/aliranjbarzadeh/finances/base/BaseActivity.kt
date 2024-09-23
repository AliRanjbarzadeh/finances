package ir.aliranjbarzadeh.finances.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.aliranjbarzadeh.finances.base.helpers.LanguageHelper
import ir.aliranjbarzadeh.finances.base.helpers.LocaleHelper
import ir.aliranjbarzadeh.finances.base.helpers.PackageHelper
import ir.aliranjbarzadeh.finances.base.util.Logger
import ir.tapsell.plus.TapsellPlus
import ir.tapsell.plus.TapsellPlusInitListener
import ir.tapsell.plus.model.AdNetworkError
import ir.tapsell.plus.model.AdNetworks
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
		binding = DataBindingUtil.setContentView(this, resId)

		if (PackageHelper.isDebuggable(this)) {
			TapsellPlus.setDebugMode(Log.DEBUG)
		}

		TapsellPlus.initialize(this, Configs.TAPSELL.TOKEN, object : TapsellPlusInitListener {
			override fun onInitializeSuccess(adNetworks: AdNetworks) {
				logger.info("Tapsell initialized: ${adNetworks.name}")
			}

			override fun onInitializeFailed(adNetworks: AdNetworks, adNetworkError: AdNetworkError) {
				logger.info("Tapsell initialize failed: ${adNetworks.name}, error is ${adNetworkError.errorMessage}")
			}

		})
	}

//	override fun onResume() {
//		LocaleHelper.setLocale(applicationContext, LanguageHelper.getLanguage())
//		super.onResume()
//	}
}