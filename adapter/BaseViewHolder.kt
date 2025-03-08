package com.example.applocker.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseViewHolder<B : ViewBinding>(
    val binding: B
) : RecyclerView.ViewHolder(binding.root) {

    public constructor(
        parent: ViewGroup,
        inflater: (LayoutInflater, ViewGroup?, Boolean) -> B,
    ) : this(
        inflater(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    )

    protected val context: Context
        get() = binding.root.context
}
