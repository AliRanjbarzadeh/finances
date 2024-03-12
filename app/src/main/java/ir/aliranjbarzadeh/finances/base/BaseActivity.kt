package ir.aliranjbarzadeh.finances.base

import android.content.Context
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.findNavController
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.di.Logger
import ir.aliranjbarzadeh.finances.base.helpers.LanguageHelper
import ir.aliranjbarzadeh.finances.base.helpers.LocalHelper
import javax.inject.Inject

abstract class BaseActivity<VDB : ViewDataBinding>(@LayoutRes private val resId: Int, @IdRes private val baseNavHostId: Int) : AppCompatActivity() {
	lateinit var binding: VDB

	@Inject
	lateinit var logger: Logger

	override fun attachBaseContext(newBase: Context) {
		LocalHelper.onAttach(
			context = newBase,
			language = LanguageHelper.getLanguage()
		)
		super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, resId)

		//override onBackPressed
		onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
			override fun handleOnBackPressed() {
				val navController = getCurrentNavController()
				if (navController.currentDestination?.id == R.id.homeFragment) {
					finish()
				} else {
					navController.popBackStack()
				}
			}
		})
	}

	override fun onSupportNavigateUp(): Boolean {
		return getCurrentNavController().navigateUp() || super.onSupportNavigateUp()
	}

	private fun getCurrentNavController(): NavController {
		return findNavController(baseNavHostId)
	}
}