package com.example.leafapp.history

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.leafapp.dataclass.PlantClass
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlant(plant : PlantClass)


    @Query("select * from PlantClass")
    fun getAllPlants():List<PlantClass>







    @Database(entities = [PlantClass::class], version = 1, exportSchema = false)
    abstract class PlantRoomDatabase: RoomDatabase() {
        abstract val dao : HistoryDao

        companion object {

            @Volatile
            private var Instance: PlantRoomDatabase? = null

            fun getInstance(context: Context): PlantRoomDatabase {

                synchronized(this)
                {
                    var instance = Instance
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            PlantRoomDatabase::class.java,
                            "plant_database"
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                        Instance = instance
                    }
                    return instance
                }

            }
        }
    }

}