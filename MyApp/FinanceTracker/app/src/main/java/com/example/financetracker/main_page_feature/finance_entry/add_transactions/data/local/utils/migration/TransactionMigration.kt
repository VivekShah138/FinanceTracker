import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val TRANSACTIONS_MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Drop the old table to remove previous entries
        db.execSQL("DROP TABLE IF EXISTS TransactionsEntity")

        // Create the new table with the new column `transactionName`
        db.execSQL(
            """CREATE TABLE TransactionsEntity (
                transactionId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                transactionName TEXT NOT NULL,
                amount REAL NOT NULL,
                currency TEXT,
                convertedAmount REAL,
                exchangeRate REAL,
                transactionType TEXT NOT NULL,
                category TEXT NOT NULL,
                dateTime INTEGER NOT NULL,
                userUid TEXT NOT NULL,
                description TEXT,
                isRecurring INTEGER NOT NULL,
                cloudSync INTEGER NOT NULL
            )"""
        )
    }
}

val TRANSACTIONS_MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create the new table for deleted transactions
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `DeletedTransactionsEntity` (
                `transactionId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `userUid` TEXT NOT NULL
            )
        """.trimIndent())
    }
}

