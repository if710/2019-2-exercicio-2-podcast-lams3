package br.ufpe.cin.android.podcast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ItemFeed(
    @ColumnInfo val title: String,
    @ColumnInfo val link: String,
    @ColumnInfo val pubDate: String,
    @ColumnInfo val description: String,
    @PrimaryKey val downloadLink: String) : Serializable
