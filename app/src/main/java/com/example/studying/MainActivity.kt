package com.example.studying

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etItem: EditText
    private lateinit var btnAdd: Button
    private lateinit var lstItems: ListView

    private lateinit var adapter: ArrayAdapter<String>
    private var items = ArrayList<String>()

    private val ITEMS_KEY = "todo_items"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etItem = findViewById(R.id.etItem)
        btnAdd = findViewById(R.id.btnAdd)
        lstItems = findViewById(R.id.lstItems)

        // Restore old list (after rotation)
        if (savedInstanceState != null) {
            val savedList = savedInstanceState.getStringArrayList(ITEMS_KEY)
            if (savedList != null) items = savedList
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lstItems.adapter = adapter

        btnAdd.setOnClickListener {
            val task = etItem.text.toString().trim()

            if (task.isEmpty()) {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show()
            } else {
                items.add(task)
                adapter.notifyDataSetChanged()
                etItem.text.clear()
            }
        }

        lstItems.setOnItemClickListener { _, _, position, _ ->
            val clickedTask = items[position]
            Toast.makeText(this, "Clicked: $clickedTask", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(ITEMS_KEY, items)
    }
}
