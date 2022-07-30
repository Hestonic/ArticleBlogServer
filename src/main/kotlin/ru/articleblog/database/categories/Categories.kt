package ru.articleblog.database.categories

import org.jetbrains.exposed.sql.Table

object Categories : Table("categories") {
    private val id = Categories.integer("id")
    private val category = Categories.varchar("category", 50)
}