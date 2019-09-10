package org.phenoapps.intercross.data

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.phenoapps.intercross.data.PollenGroupDao
import java.util.*


class PollenGroupRepository private constructor(
        private val pollenGroupDao: PollenGroupDao
) {
    suspend fun createPollenSet(name: String) {
        withContext(IO) {
             pollenGroupDao.insert(PollenGroup(null, name, UUID.randomUUID().toString()))
        }
    }

    suspend fun update(vararg p: PollenGroup?) {
        withContext(IO) {
            pollenGroupDao.update(*p)
        }
    }

    suspend fun delete(vararg p: PollenGroup?) {
        withContext(IO) {
            pollenGroupDao.delete(*p)
        }
    }

    fun getAll() = pollenGroupDao.getAll()

    companion object {
        @Volatile private var instance: PollenGroupRepository? = null

        fun getInstance(groupDao: PollenGroupDao) =
                instance ?: synchronized(this) {
                    instance ?: PollenGroupRepository(pollenGroupDao = groupDao)
                            .also { instance = it }
                }
    }
}