package ir.aliranjbarzadeh.finances.data.models

import android.os.Parcelable
import ir.aliranjbarzadeh.finances.presentation.FilterType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
	var type: FilterType,
	var value: String,
) : Parcelable