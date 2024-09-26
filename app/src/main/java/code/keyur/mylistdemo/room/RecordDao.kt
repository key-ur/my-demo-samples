package code.keyur.mylistdemo.room

import androidx.room.*
import code.keyur.mylistdemo.ui.slideshow.Record

@Dao
interface RecordDao {

    // -- read -- //
    @Query("SELECT * FROM Record")
    fun getAllRecords(): List<Record>

    // -- insert -- //
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecords(vararg records: Record):List<Long>

    // -- update -- //
    @Update
    fun updateRecord(record: Record): Int

}