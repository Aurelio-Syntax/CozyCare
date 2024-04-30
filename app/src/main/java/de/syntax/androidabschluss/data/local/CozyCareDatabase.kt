package de.syntax.androidabschluss.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.syntax.androidabschluss.data.model.DiaryEntries
import de.syntax.androidabschluss.data.model.Events
import de.syntax.androidabschluss.data.model.Tasks

@Database(entities = [Events::class, Tasks::class, DiaryEntries::class], version = 6, exportSchema = false)
abstract class CozyCareDatabase : RoomDatabase() {
    abstract val cozyCareDao: CozyCareDao
}

private lateinit var INSTANCE: CozyCareDatabase

fun getDatabase(context: Context): CozyCareDatabase {
    synchronized(CozyCareDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                CozyCareDatabase::class.java,
                "cozyCare_database"
            )   //fallbackToDestructiveMigration()
                .build()
        }
        return INSTANCE
    }

}