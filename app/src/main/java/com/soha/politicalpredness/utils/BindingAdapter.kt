package com.soha.politicalpredness.utils

import android.widget.Spinner
import androidx.databinding.BindingAdapter

@BindingAdapter("state")
fun state(v: Spinner, value: String?) {
    for (i in 0..v.adapter.count - 1) {
        if (v.adapter.getItem(i).toString() == value) {
            v.setSelection(i)
        }
    }
}