package com.fgomes.wl_flavors.config

import android.view.View
import javax.inject.Inject

class ConfigImpl @Inject constructor(): Config {

    override val addButtonVisibility: Int = View.GONE
}