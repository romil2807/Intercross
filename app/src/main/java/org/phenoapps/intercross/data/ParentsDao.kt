package org.phenoapps.intercross.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ParentsDao : BaseDao<Parents> {

    @Query("SELECT * FROM parents")
    fun getAll(): LiveData<List<Parents>>

}