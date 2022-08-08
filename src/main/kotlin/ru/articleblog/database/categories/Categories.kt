package ru.articleblog.database.categories

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Categories : Table("categories") {
    private val id = Categories.integer("id")
    private val category = Categories.varchar("category", 50)

    fun fetchCategoryById(id: Int): CategoryDTO = transaction {
            Categories.select { Categories.id eq id }.map(::resultRowToCategoryDTO).single()
        }

    fun fetchAllCategories(): List<CategoryDTO> = transaction {
        Categories.selectAll().map(::resultRowToCategoryDTO)
    }

    private fun resultRowToCategoryDTO(row: ResultRow) = CategoryDTO(
        id = row[id],
        category = row[category]
    )
}