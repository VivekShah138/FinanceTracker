
# Transaction Room Database Schema Changelog

This file maintains the history of schema changes in the Finance Tracker App's Transactions Room Database.

---

## Version 1
> Initial Database Setup

### Table: `TransactionsEntity`
| Column Name     | Type    | Notes              |
|-----------------|---------|-------------------|
| transactionId   | Int     | Primary Key (Auto Generated) |
| amount          | Double  | Transaction amount |
| category        | String  | Transaction category |
| dateTime        | Long    | Stored as timestamp (milliseconds) |
| transactionType | String  | Income / Expense etc |
| userUid         | String  | Firebase User UID |
| description     | String? | Optional description |
| isRecurring     | Boolean | True / False |
| cloudSync       | Boolean | Synced with cloud or not |

---

## Version 2
> Added new column `transactionName` (String)

| Column Name       | Type    | Notes               |
|------------------|---------|--------------------|
| transactionName   | String  | Name/Title of the transaction |

---

## Version 3
> Added `DateTypeConverters` class  
(Room now converts `Date <-> Long` automatically)

- No table/column changes.
- Only added `TypeConverters` for better type safety.

---

# Notes:
- `dateTime` column was always stored as `Long` (timestamp in milliseconds since epoch).
- Version 3 only improves developer experience — no migration logic required because `Long` values were always being saved.

---

# Migration Summary

| Version | Changes Made               | Migration Needed? |
|---------|----------------------------|------------------|
| v1      | Initial setup              | No               |
| v2      | Added `transactionName`    | Yes (Column Added)|
| v3      | Added `DateTypeConverters` | No (Handled by Room)|
