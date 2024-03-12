package ir.aliranjbarzadeh.finances.presentation.setting

import android.os.Bundle
import android.view.View
import ir.aliranjbarzadeh.finances.R
import ir.aliranjbarzadeh.finances.base.BaseFragment
import ir.aliranjbarzadeh.finances.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting, R.string.setting_menu, false) {
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		toggleBackButton(false)
	}
}