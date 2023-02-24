package com.example.leafapp.dataclass

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
@Entity()
data class PostClass(
    @PrimaryKey
    var title: String,
    var photo: String,
    var type: String,
    var topics: String,
    var contents: String,
    var doc: String,
) : Parcelable
