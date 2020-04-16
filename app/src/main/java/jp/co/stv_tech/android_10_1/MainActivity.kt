package jp.co.stv_tech.android_10_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    //選択されたカクテルのID（行）
    private var _cocktialdId = -1

    //選択されたカクテルの名前
    private var _cocktailName = ""

    private var _helper = DatabaseHelper(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvCocktail = findViewById<ListView>(R.id.lvCocktail)
        lvCocktail.onItemClickListener = ListItemClickListener()
    }

    override fun onDestroy() {
        _helper.close()
        super.onDestroy()
    }

    //保存ボタンを押下されたとき
    fun onSaveButtonClick(view: View) {
        val etNote = findViewById<EditText>(R.id.etNote)

        val note = etNote.text.toString()

        val db = _helper.writableDatabase

        //メモを削除
        val sqliteDelete = "DELETE FROM cocktailmemos WHERE _id = ?"
        var stmt = db.compileStatement(sqliteDelete)
        stmt.bindLong(1, _cocktialdId.toLong())
        stmt.executeUpdateDelete()

        //メモを追加
        val sqlInser = "INSERT INTO cocktailmemos (_id, name, note) VALUES (? , ? , ?)"
        stmt = db.compileStatement(sqlInser)
        stmt.bindLong(1, _cocktialdId.toLong())
        stmt.bindString(2,_cocktailName)
        stmt.bindString(3, note)
        stmt.executeInsert()

        etNote.setText("")

        val tvCocktailName = findViewById<TextView>(R.id.tvCoctakiName)
        tvCocktailName.text = getString(R.string.tv_name)

        val btnSave = findViewById<Button>(R.id.btButton)
        btnSave.isEnabled = false
    }

    private inner class ListItemClickListener: AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            Log.d("ListItemClickListener", "${position}")

            _cocktialdId = position

            _cocktailName = parent?.getItemAtPosition(position) as String

            val tvCocktailName = findViewById<TextView>(R.id.tvCoctakiName)
            tvCocktailName.text = _cocktailName

            val btnSave = findViewById<Button>(R.id.btButton)
            btnSave.isEnabled = true

            //DBの操作
            val db = _helper.writableDatabase

            //メモの取得
            val sql = "SELECT * FROM cocktailmemos WHERE _id = ${_cocktialdId}"

            val cusor = db.rawQuery(sql, null)

            var note = ""

            while (cusor.moveToNext()) {
                val idNote = cusor.getColumnIndex("note")
                note = cusor.getString(idNote)
            }

            val etNote = findViewById<EditText>(R.id.etNote)
            etNote.setText(note)
        }
    }
}
