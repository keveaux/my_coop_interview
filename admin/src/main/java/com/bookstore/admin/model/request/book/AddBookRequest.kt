package com.bookstore.admin.model.request.book

import com.bookstore.admin.constant.Clothes_Coop
import org.threeten.bp.LocalDateTime

data class AddBookRequest(
    val authorName: String,
    val bookCategoryId: Int,
    val clothesCoop: String = Clothes_Coop.FOR_SELL.toString(),
    val isbn: String = "null",
    val price: Int = 0,
    val publicationDate: String = LocalDateTime.now().toString(),
    val synopsis: String,
    val title: String
)