package com.example.shoetracker



import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: GarmentViewModel by viewModels()
    private lateinit var adapter: GarmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = GarmentAdapter { item ->
            showDeleteConfirmation(item)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.allItems.collect { items ->
                adapter.updateItems(items)
            }
        }

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_item, null)
        val etName = dialogView.findViewById<EditText>(R.id.etName)
        val etCategory = dialogView.findViewById<EditText>(R.id.etCategory)
        val etDays = dialogView.findViewById<EditText>(R.id.etDays)

        AlertDialog.Builder(this)
            .setTitle("Добавить вещь")
            .setView(dialogView)
            .setPositiveButton("Добавить") { _, _ ->
                val name = etName.text.toString().trim()
                val category = etCategory.text.toString().trim()
                val daysStr = etDays.text.toString().trim()

                if (name.isNotEmpty() && category.isNotEmpty() && daysStr.isNotEmpty()) {
                    val days = daysStr.toIntOrNull() ?: 7
                    val newItem = GarmentItem(
                        name = name,
                        category = category,
                        lastCleanedDate = System.currentTimeMillis(),
                        cleaningIntervalDays = days
                    )
                    viewModel.addItem(newItem)
                } else {
                    Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun showDeleteConfirmation(item: GarmentItem) {
        AlertDialog.Builder(this)
            .setTitle("Удалить?")
            .setMessage("Удалить «${item.name}» из трекера?")
            .setPositiveButton("Да") { _, _ ->
                viewModel.deleteItem(item)
            }
            .setNegativeButton("Нет", null)
            .show()
    }
}