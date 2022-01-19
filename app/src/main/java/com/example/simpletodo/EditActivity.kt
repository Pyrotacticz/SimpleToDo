package com.example.simpletodo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.EditText

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val getTextToEdit = intent.getStringExtra("text")
        val editText = findViewById<EditText>(R.id.editItem)
        editText.setText(getTextToEdit)
        editText.requestFocus()
        editText.imeOptions = EditorInfo.IME_ACTION_DONE;
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);

        editText.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                val data = Intent()
                data.putExtra("text", editText.text.toString().trim().replace("\n", " "))
                data.putExtra("position", intent.getStringExtra("position"))
                setResult(RESULT_OK, data)
                finish()
                true
            }
            false
        }
    }
}