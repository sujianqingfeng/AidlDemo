package com.sujian.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AidlActivity extends AppCompatActivity {


    private Button button;
    private IBook iBook;
    private boolean isCon;



    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isCon=true;
            iBook=IBook.Stub.asInterface(service);
            if (iBook!=null){
                try {
                    Log.e("TAG","Service result--"+iBook.getBooks().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isCon=false;
            Log.e("TAG","Service disCon");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        button= (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCon){
                    startCon();
                    Toast.makeText(AidlActivity.this,"正在连接***",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (iBook==null)return;;

                Book book=new Book();
                book.setName("Test");
                book.setPrice(123);
                try {
                    iBook.addBook(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void startCon(){
        Intent intent=new Intent();
        intent.setAction("com.sujian.aidl");
        intent.setPackage("com.sujian.aidldemo");
        bindService(intent,connection,BIND_AUTO_CREATE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!isCon)
            startCon();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (isCon){
            unbindService(connection);
            isCon=false;
        }
    }
}
