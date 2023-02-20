package com.example.leafapp.ui.home.homemenus

import android.app.Application
import androidx.lifecycle.*
import com.example.leafapp.dataclass.PlantClass
import com.example.leafapp.history.HistoryDao
import com.example.leafapp.history.HistoryRepoImpl
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application):AndroidViewModel(application) {
    private val plantsList=MutableLiveData<List<PlantClass>>()

}