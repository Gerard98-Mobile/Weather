package pl.gg.weather.ui.views

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import pl.gg.weather.R
import pl.gg.weather.databinding.ViewMyEditTextBinding
import pl.gg.weather.functional.validator.InputValidator
import pl.gg.weather.util.str

class TextInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : StateFrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewMyEditTextBinding.inflate(LayoutInflater.from(context), this, true)
    var inputValidator : InputValidator? = null

    var error : String?
        get(){
            return binding.container.error?.toString()
        }
        set(value){
            binding.container.error = value
        }

    var text: String
        get(){
            return binding.input.text.toString()
        }
        set(value){
            binding.input.setText(value)
        }

    var hint : String?
        get(){
            return binding.input.hint.toString()
        }
        set(value){
            binding.input.hint = value
        }

    init{
        context.theme.obtainStyledAttributes(attrs, R.styleable.TextInputView, defStyleAttr, 0).apply {
            binding.input.id = generateViewId()

            hint = getString(R.styleable.TextInputView_hint)

            recycle()
        }
    }

    fun validate(): Boolean{
        error = if(inputValidator?.regex?.matches(text) == true){
            inputValidator?.errorMessage?.str(context)
        }else{
             null
        }
        return isValid()
    }

    fun isValid() : Boolean{
        return error.isNullOrEmpty()
    }

    /* State managment */
    private val TEXT_KEY = "text_key"

    override var saveState: () -> Bundle? = {
        Bundle().apply {
            putString(TEXT_KEY, binding.input.text.toString())
        }
    }

    override var onRestoreState: (Bundle) -> Unit = {
        binding.input.setText(it.getString(TEXT_KEY))
    }


}