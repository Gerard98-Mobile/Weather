package pl.gg.weather.core

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

@SuppressWarnings("unchecked")
open class MultiRecyclerAdapter<T>(val context: Context) : RecyclerView.Adapter<VH<ViewBinding>>(){

    class RegisteredItem<T, BINDING : ViewBinding>(
        val condition: (Any) -> Boolean,
        val binder: Binder<T, BINDING>,
        val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BINDING
    )

    abstract class Binder<T, BINDING : ViewBinding>{
        abstract fun binding(a: VH<BINDING>, b: T)
    }

    private val items = mutableListOf<RegisteredItem<T, ViewBinding>>()

    var data = listOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun <K, BINDING : ViewBinding> register(
        condition: (Any) -> Boolean,
        bind: (VH<BINDING>, K) -> Unit,
        bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BINDING
    ){
        val binder = object : Binder<K, BINDING>() {
            override fun binding(a: VH<BINDING>, b: K){
                bind(a, b)
            }
        }

        items.add(RegisteredItem(condition, binder as Binder<T, ViewBinding>, bindingInflater))
    }

    fun <K, BINDING : ViewBinding> register(
        condition: (Any) -> Boolean,
        vh: RecyclerView.ViewHolder,
        bind: (VH<BINDING>, K) -> Unit,
        bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BINDING
    ){
        val binder = object : Binder<K, BINDING>() {
            override fun binding(a: VH<BINDING>, b: K){
                bind(a, b)
            }
        }

        items.add(RegisteredItem(condition, binder as Binder<T, ViewBinding>, bindingInflater))
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<ViewBinding> {
        items.forEach {
            if(it.condition(data[viewType])){
                val vh = it.bindingInflater.invoke(LayoutInflater.from(context), parent, false)
                return VH(vh)
            }
        }
        throw NotRegisteredException("Did you forgot about registering view? :D")
    }

    override fun onBindViewHolder(holder: VH<ViewBinding>, position: Int) {
        items.forEach {
            if(it.condition(data[position])){
                it.binder.binding(holder, data[position] as T)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}

class NotRegisteredException(msg: String) : Throwable(msg)

class VH<BINDING>(val binding: BINDING) : RecyclerView.ViewHolder(binding.root) where BINDING : ViewBinding