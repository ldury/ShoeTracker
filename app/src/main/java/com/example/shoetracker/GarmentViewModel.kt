package com.example.shoetracker



import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GarmentViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: GarmentDao = AppDatabase.getDatabase(application).garmentDao()
    val allItems: Flow<List<GarmentItem>> = dao.getAllItems()

    fun addItem(item: GarmentItem) {
        viewModelScope.launch {
            dao.insert(item)
        }
    }

    fun deleteItem(item: GarmentItem) {
        viewModelScope.launch {
            dao.delete(item)
        }
    }
}