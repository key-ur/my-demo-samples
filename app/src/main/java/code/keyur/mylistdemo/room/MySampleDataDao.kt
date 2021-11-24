package code.keyur.mylistdemo.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import code.keyur.mylistdemo.ui.slideshow.MySampleData
import code.keyur.mylistdemo.ui.slideshow.Record

@Dao
interface MySampleDataDao {

    // -- read -- //
    @Query("SELECT * FROM MySampleData")
    fun getAllMySampleData(): List<MySampleData>


    // -- insert -- //
    @Insert
    fun insertOneOrMoreMySampleData(vararg mySampleDataItems: MySampleData): List<Long>

    // -- update -- //
    @Query("UPDATE MySampleData SET records=:records")
    fun updateRecords(records: List<Record>): Int


    // -- delete -- //
}