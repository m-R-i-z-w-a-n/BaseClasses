package com.example.applocker.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.example.applocker.R
import com.example.applocker.databinding.ItemAppBinding
import com.example.applocker.di.DiComponent
import com.example.applocker.models.Application
import com.example.applocker.ui.base.BaseAdapter

class AppsAdapter(private val diComponent: DiComponent): BaseAdapter<Application, ItemAppBinding>(
    bindingFactory = ItemAppBinding::inflate,
    callback = object : DiffUtil.ItemCallback<Application>() {
        override fun areItemsTheSame(oldItem: Application, newItem: Application): Boolean {
            return oldItem.pkgName == newItem.pkgName
        }

        override fun areContentsTheSame(oldItem: Application, newItem: Application): Boolean {
            return oldItem == newItem
        }
    }
) {

    private val lockedApps get() = diComponent.lockerPreferences.lockedApplications

    override fun ItemAppBinding.bindViews(model: Application) {
        appName.text = model.appName
        packageName.text = model.pkgName
        model.appIcon?.let(icon::setImageDrawable)

        if (lockedApps.contains(model.pkgName)) {
            imgLockUnlock.setImageResource(R.drawable.ic_locked)
        } else {
            imgLockUnlock.setImageResource(R.drawable.ic_unlocked)
        }
    }

    override fun ItemAppBinding.bindListeners(position: Int, model: Application) {
        imgLockUnlock.setOnClickListener {
            lockUnlockAppListener?.invoke(position, model.pkgName.orEmpty())
        }
    }

    private var lockUnlockAppListener: ((index: Int, pkgName: String) -> Unit)? = null
    fun setLockUnlockListener(listener: (index: Int, pkgName: String) -> Unit) {
        lockUnlockAppListener = listener
    }
}