# newemployee_android_10

## DBHelper
テーブルの作成(CREATE TABLE)

```
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.StringBuilder

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object  {
        private const val DATABASE_NAME = "cocktailmemo.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {

        //テーブルの生成
        val sb = StringBuilder()
        sb.append("CREATE TABLE cocktailmemos (")
        sb.append("_id INTEGER PRIMARY KEY,")
        sb.append("name TEXT,")
        sb.append("note TEXT")
        sb.append(");")

        val sql = sb.toString()
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}
```

## データ登録 (INSERT)

```
val db = _helper.writableDatabase

val sqlInser = "INSERT INTO cocktailmemos (_id, name, note) VALUES (? , ? , ?)"
stmt = db.compileStatement(sqlInser)
stmt.bindLong(1, _cocktialdId.toLong())
stmt.bindString(2,_cocktailName)
stmt.bindString(3, note)
stmt.executeInsert()
```        

## データ削除 (DELETE)

```
val db = _helper.writableDatabase

val sqliteDelete = "DELETE FROM cocktailmemos WHERE _id = ?"
var stmt = db.compileStatement(sqliteDelete)
stmt.bindLong(1, _cocktialdId.toLong())
stmt.executeUpdateDelete()
```        

## データ参照 (SELECT)

```
val db = _helper.writableDatabase

val sql = "SELECT _id, name, note FROM cocktailmemos WHERE _id = ${_cocktialdId}"
val cusor = db.rawQuery(sql, null)

var note = ""

while (cusor.moveToNext()) {
   val idNote = cusor.getColumnIndex("note")
   note = cusor.getString(idNote)
}                

```        

