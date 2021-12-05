package pl.gg.weather.ui.views

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.FrameLayout
import pl.gg.weather.functional.SavedState

abstract class StateFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    abstract var saveState : () -> Bundle?
    abstract var onRestoreState : (Bundle) -> Unit

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
        when(state){
            is SavedState -> {
                onRestoreState.invoke(state.bundle ?: return)
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply { bundle = saveState.invoke() }
    }
}