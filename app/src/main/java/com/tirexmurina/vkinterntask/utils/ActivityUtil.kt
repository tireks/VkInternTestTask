package com.tirexmurina.vkinterntask.utils

import androidx.fragment.app.Fragment
import com.tirexmurina.vkinterntask.MainActivity

val Fragment.mainActivity: MainActivity
    get() = requireActivity() as MainActivity