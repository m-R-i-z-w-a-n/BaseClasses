package com.example.applocker.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.example.applocker.utils.getSafe

typealias GenericCallback<T> = (T) -> Unit

abstract class BaseAdapter<Model : Any, Binding : ViewBinding>(
    private val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> Binding,
    callback: DiffUtil.ItemCallback<Model> = BaseDiffUtils()
): ListAdapter<Model, BaseViewHolder<Binding>>(callback) {

    protected var itemCallback: GenericCallback<Any>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Binding> {
        return BaseViewHolder(bindingFactory.invoke(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Binding>, position: Int) {
        val item = currentList.getSafe(holder.adapterPosition) ?: return

        with(holder) {
            binding.root.tag = adapterPosition
            binding.bindViews(item)
            binding.bindListeners(adapterPosition)
            binding.bindListeners(item)
            binding.bindListeners(adapterPosition, item)
        }
    }

    override fun submitList(list: List<Model>?) {
        super.submitList(list?.toMutableList())
    }

    protected abstract fun Binding.bindViews(model: Model)
    open fun Binding.bindListeners(model: Model) {}
    open fun Binding.bindListeners(position: Int) {}
    open fun Binding.bindListeners(position: Int, model: Model) {}

    fun onItemClickListener(callback: GenericCallback<Any>) {
        itemCallback = callback
    }
}
