package ir.aliranjbarzadeh.finances.presentation

import android.graphics.Typeface
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseActivity
import ir.aliranjbarzadeh.finances.base.extensions.changeFont
import ir.aliranjbarzadeh.finances.base.extensions.observe
import ir.aliranjbarzadeh.finances.base.extensions.setupWithNavigationController
import ir.aliranjbarzadeh.finances.base.helpers.FontHelper
import ir.aliranjbarzadeh.finances.base.interfaces.util.NavigationCallback
import ir.aliranjbarzadeh.finances.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main, R.id.base_nav_host) {

	private val viewModel: MainViewModel by viewModels()

	private lateinit var navController: NavController

	override fun onCreate(savedInstanceState: Bundle?) {
		installSplashScreen()
		super.onCreate(savedInstanceState)

		//bottom navigation
		setupBottomNavigation()

		//observers
		setupObservers()

		viewModel.seedDatabase()
	}

	private fun setupObservers() {
		viewModel.run {
			observe(isLoading(), ::initLoading)
		}
	}

	private fun initLoading(isLoading: Boolean) {}


	private fun setupBottomNavigation() {
		//Set font
		FontHelper.getTypeFace(this)?.also { typeface: Typeface ->
			binding.bottomNavigation.changeFont(typeface)
		}

		val navGraphIds = listOf(
			R.navigation.home_nav,
			R.navigation.profile_nav,
			R.navigation.setting_nav,
		)

		val navCallbacks = listOf<NavigationCallback?>(null, null, null)

		binding.bottomNavigation.setupWithNavigationController(
			navGraphIds, navCallbacks,
			supportFragmentManager,
			R.id.base_nav_host,
			intent
		).observe(this) { mNavController: NavController ->
			navController = mNavController
		}
	}
}