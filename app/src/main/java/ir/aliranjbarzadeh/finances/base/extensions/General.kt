package ir.aliranjbarzadeh.finances.base.extensions

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.core.text.toSpannable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import io.github.inflationx.calligraphy3.CalligraphyTypefaceSpan
import io.github.inflationx.calligraphy3.TypefaceUtils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

fun getSpannableTextFont(context: Context, text: String, fontPath: String): Spannable {
	val sBuilder = SpannableStringBuilder()
	sBuilder.append(text)

	val typefaceSpan = CalligraphyTypefaceSpan(TypefaceUtils.load(context.assets, fontPath))
	sBuilder.setSpan(typefaceSpan, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

	return sBuilder.toSpannable()
}

fun <T : ViewModel> Fragment.obtainViewModel(
	owner: ViewModelStoreOwner,
	viewModelClass: Class<T>,
	viewModelFactory: ViewModelProvider.Factory
) = ViewModelProvider(owner, viewModelFactory).get(viewModelClass)


fun JSONObject.toRequestBody(): RequestBody {
	return this.toString().toRequestBody("application/json".toMediaType())
}