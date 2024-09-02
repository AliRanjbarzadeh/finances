package ir.aliranjbarzadeh.finances.presentation

import android.graphics.Typeface
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseActivity
import ir.aliranjbarzadeh.finances.base.extensions.changeFont
import ir.aliranjbarzadeh.finances.base.extensions.observe
import ir.aliranjbarzadeh.finances.base.helpers.FontHelper
import ir.aliranjbarzadeh.finances.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

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

	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp()
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

		val navHostFragment = supportFragmentManager.findFragmentById(R.id.base_nav_host) as NavHostFragment
		navController = navHostFragment.navController

		binding.bottomNavigation.setupWithNavController(navController)
	}
}