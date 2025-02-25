package com.bookstore.ui.main.fragment.book

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bookstore.constant.BookStatus
import com.bookstore.constant.RetrofitStatus
import com.bookstore.model.formatted.book.BookResponse
import com.bookstore.repository.BookRepository
import com.bookstore.utils.Retrofit.printRetrofitError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class BookViewModel(
    application: Application,
    private val bookRepository: BookRepository
) : AndroidViewModel(application) {

    private val _bookResponse = MutableLiveData<BookResponse>()
    val bookResponse: LiveData<BookResponse> = _bookResponse

    fun getBook() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val result = bookRepository.getBook().sortedBy { it.id }.filter { it.bookStatus == BookStatus.FOR_SELL.toString() }
            if(result.isNotEmpty()) _bookResponse.postValue(BookResponse(RetrofitStatus.SUCCESS, result))
            else {
                _bookResponse.postValue(BookResponse(RetrofitStatus.EMPTY))
                Log.e(this::class.java.simpleName, result.toString())
            }
        } catch(throwable: Throwable) {
            if(throwable is HttpException && throwable.code() == 401)
                _bookResponse.postValue(BookResponse(RetrofitStatus.UNAUTHORIZED))
            else
                _bookResponse.postValue(BookResponse(RetrofitStatus.FAILURE))
            throwable.printRetrofitError()
        }
    }
}