package com.sujian.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ADILService extends Service {

    public final String TAG=this.getClass().getSimpleName();
    private List<Book> mBooks=new ArrayList<>();


    private final IBook.Stub mIBook=new IBook.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {

            synchronized (this){
                Log.e(TAG,"invoking getBooks()----"+mBooks.toString());

                if (mBooks!=null){
                    return mBooks;
                }
            }
            return new ArrayList<>();
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (this){
                if (mBooks==null){
                    Log.e(TAG,"book list is null");
                    mBooks=new ArrayList<>();
                }

                if (book==null){
                    Log.e(TAG,"book is null");
                    book=new Book();
                }

                book.setPrice(1111);
                if (!mBooks.contains(book)){
                    mBooks.add(book);
                }

                Log.e(TAG,"invoking add book ----book list--"+mBooks.toString());
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        Book book=new Book();
        book.setPrice(100);
        book.setName("素笺");
        mBooks.add(book);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onbind");
        return mIBook;
    }
}
