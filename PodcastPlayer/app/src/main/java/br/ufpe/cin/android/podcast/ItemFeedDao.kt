package br.ufpe.cin.android.podcast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemFeedDao {
    @Query("SELECT * FROM ItemFeed")
    fun getAll(): List<ItemFeed>

    // Atualiza elementos caso ja existam
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg itemFeeds: ItemFeed)
} 