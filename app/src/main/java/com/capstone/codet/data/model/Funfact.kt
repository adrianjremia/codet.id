package com.capstone.codet.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Funfact (
    var title:String,
    var desc:String,
):Parcelable