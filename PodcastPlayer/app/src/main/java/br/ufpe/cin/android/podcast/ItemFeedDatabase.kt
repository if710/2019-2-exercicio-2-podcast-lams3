package br.ufpe.cin.android.podcast

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room



@Database(entities = [ItemFeed::class], version = 1)
abstract class ItemFeedDatabase : RoomDatabase() {
    abstract fun itemFeedDao(): ItemFeedDao

    // Implementado como singleton para maior performance/seguranca
    companion object {
        private var instance: ItemFeedDatabase? = null
        private val sLock = Any()

        fun getInstance(context: Context): ItemFeedDatabase {
            // Evita que outra instancia seja criada quando chamado a partir de outra thread
            synchronized (sLock) {
                if (instance == null) {
                    // Cria a base de dados
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemFeedDatabase::class.java, "ItemFeedDatabase.db"
                    ).build()
                }
                return instance!!
            }
        }
    }
}