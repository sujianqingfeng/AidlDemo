// IBook.aidl
package com.sujian.aidldemo;
import com.sujian.aidldemo.Book;
// Declare any non-default types here with import statements

interface IBook {
     List<Book> getBooks();
     void addBook(in Book book);
}
