package com.example.shoetracker



import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity означает, что этот класс будет таблицей в базе данных
@Entity(tableName = "garment_items")
data class GarmentItem(
    @PrimaryKey(autoGenerate = true) // ID будет создаваться автоматически
    val id: Int = 0,
    val name: String, // Название (например, "Кроссовки Nike")
    val category: String, // Категория: "Обувь" или "Одежда"
    val lastCleanedDate: Long, // Дата последней чистки (храним в миллисекундах)
    val cleaningIntervalDays: Int // Через сколько дней снова чистить
)