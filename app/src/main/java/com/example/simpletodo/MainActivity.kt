package com.example.simpletodo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnClickListener {
            override fun onItemLongClicked(position: Int) {
                listOfTasks.removeAt(position)

                adapter.notifyItemRemoved(position)

                saveItems()
            }

            override fun onItemClicked(position: Int) {
                val i: Intent = Intent(this@MainActivity, EditActivity::class.java)
                i.putExtra("text", listOfTasks.get(position))
                i.putExtra("position", position.toString())
                startActivityForResult(i, 20)
            }
        }

        loadItems()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        findViewById<Button>(R.id.button).setOnClickListener {

            val userInputtedTask = inputTextField.text.toString()
            if (userInputtedTask.isNotEmpty()) {
                listOfTasks.add(userInputtedTask)

                adapter.notifyItemInserted(listOfTasks.size - 1)

                inputTextField.setText("")

                saveItems()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 20) {
            if (data != null) {
                val text: String? = data.getStringExtra("text")
                val pos: Int = Integer.parseInt(data.getStringExtra("position") ?: "0")

                if (text != null) {
                    listOfTasks[pos] = text
                    adapter.notifyItemChanged(pos)
                } else {
                    listOfTasks.removeAt(pos)
                    adapter.notifyItemRemoved(pos)
                }
                saveItems()
            }
        }
    }

    private fun getDataFile() : File {

        return File(filesDir, "data.txt")
    }

    private fun loadItems() {
        try {
            listOfTasks =
                org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    private fun saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}