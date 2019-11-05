package org.phenoapps.intercross.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.phenoapps.intercross.data.Pollen
import org.phenoapps.intercross.data.PollenRepository

class PollenViewModel internal constructor(
        private val repo: PollenRepository
) : ViewModel() {

    private val viewModelJob = Job()

    //coroutines do not complete until all launched children complete
    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    val pollen: LiveData<List<Pollen>> = repo.getAll()

    fun addPollen(groupId: Long, name: String) {
        viewModelScope.launch {
            repo.createPollen(groupId, name)
        }
    }

    fun delete(vararg p: Pollen) {
        viewModelScope.launch {
            repo.delete(*p)
        }
    }
    fun update(vararg p: Pollen) {
        viewModelScope.launch {
            repo.update(*p)
        }
    }
}

