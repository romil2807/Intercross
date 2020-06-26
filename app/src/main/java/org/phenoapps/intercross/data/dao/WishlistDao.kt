package org.phenoapps.intercross.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import org.phenoapps.intercross.data.models.Wishlist

@Dao
interface WishlistDao : BaseDao<Wishlist> {

    @Query("SELECT * FROM wishlist ORDER BY wishlist.wishCurrent DESC")
    fun getAll(): LiveData<List<Wishlist>>

    @Transaction
    @Query("DELETE FROM wishlist")
    fun drop()
}