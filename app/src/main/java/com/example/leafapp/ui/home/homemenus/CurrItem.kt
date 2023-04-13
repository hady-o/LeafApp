package com.example.leafapp.ui.home.homemenus

import androidx.lifecycle.MutableLiveData
import com.example.leafapp.dataclass.PostClass

object CurrItem {
    public var pos: Int = 0
    public val deletedPost = MutableLiveData<PostClass>()
    public var deleteEnable: Boolean = false

}