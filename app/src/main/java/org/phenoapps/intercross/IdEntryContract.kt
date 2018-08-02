package org.phenoapps.intercross

import android.provider.BaseColumns

internal object IdEntryContract {

    val SQL_CREATE_ENTRIES = (
            "CREATE TABLE INTERCROSS( " +
                    IdEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
                    + IdEntry.COLUMN_NAME_MALE + " TEXT,"
                    + IdEntry.COLUMN_NAME_FEMALE + " TEXT,"
                    + IdEntry.COLUMN_NAME_CROSS + " TEXT,"
                    + IdEntry.COLUMN_NAME_USER + " TEXT,"
                    + IdEntry.COLUMN_NAME_DATE + " TEXT,"
                    + IdEntry.COLUMN_NAME_LOCATION + " TEXT)")

    val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + IdEntry.TABLE_NAME

    internal class IdEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "INTERCROSS"
            val COLUMN_NAME_ID = "_id"
            val COLUMN_NAME_MALE = "male"
            val COLUMN_NAME_FEMALE = "female"
            val COLUMN_NAME_CROSS = "cross_id"
            val COLUMN_NAME_USER = "person"
            val COLUMN_NAME_DATE = "timestamp"
            val COLUMN_NAME_LOCATION = "location"
        }
    }

}
