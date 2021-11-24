package code.keyur.mylistdemo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import code.keyur.mylistdemo.ui.slideshow.Field
import code.keyur.mylistdemo.ui.slideshow.MySampleData
import code.keyur.mylistdemo.ui.slideshow.Record
import code.keyur.mylistdemo.utils.typeconverters.ListOfFieldTypeConverter
import code.keyur.mylistdemo.utils.typeconverters.ListOfRecordTypeConverter
import code.keyur.mylistdemo.utils.typeconverters.ListOfStringTypeConverter


/**
 *  + What happens in this class?
=>


 */
@Database(version = 2, entities = [MySampleData::class, Record::class, Field::class], exportSchema = false)
@TypeConverters(value = arrayOf(ListOfStringTypeConverter::class, ListOfRecordTypeConverter::class, ListOfFieldTypeConverter::class))
abstract class MyRoomDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var DB_INSTANCE: MyRoomDatabase? = null

        fun getMyDatabase(context: Context): MyRoomDatabase {
            if (DB_INSTANCE != null) {
                return DB_INSTANCE as MyRoomDatabase
            } else {
                synchronized(this) {
                    DB_INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyRoomDatabase::class.java,
                        "my-sample-room-db"
                    ).build()
                    return DB_INSTANCE as MyRoomDatabase
                }
            }
        }
    }

    abstract fun mySampleDataDao(): MySampleDataDao
    abstract fun recordDao(): RecordDao

}