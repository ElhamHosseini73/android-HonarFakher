package ir.rayapars.honarfakher.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import ir.rayapars.honarfakher.R;


public class Splash extends AppCompatActivity {


    AsyncTask asyncTask;
    TextView retry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fresco.initialize(this);

        setContentView(R.layout.splash_layout);

        retry = (TextView) findViewById(R.id.retry);

        Uri uri = Uri.parse("asset:///image/loading.gif");
//        DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(uri).setAutoPlayAnimations(true).build(); simpleDraweeView.setController(controller);

        asyncTask = new AsyncTask() {


            boolean ans = false;

            @Override
            protected Object doInBackground(Object[] objects) {

                long counter = 0;
                while (true) {
                    ans = executeCommand2();
                    if (ans == true) {
                        break;
                    }
                    counter++;
                    if (counter > 5) {
                        publishProgress();
                        counter = 0;
                    }
                }

                return null;
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                super.onProgressUpdate(values);
                // Toast.makeText(Splash.this, "تلاش مجدد برای انصال", Toast.LENGTH_LONG).show();
                retry.setVisibility(View.VISIBLE);

            }

            @Override
            protected void onPostExecute(Object o) {

                super.onPostExecute(o);
                if (ans == true) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();


                            startActivity(new Intent(Splash.this, MainActivity.class));

                        }
                    }, 5000);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }


    private boolean executeCommand2() {
        boolean exists = false;

        try {
            SocketAddress sockaddr = new InetSocketAddress("www.google.com", 80);
            // Create an unbound socket
            Socket sock = new Socket();

            // This method will block no more than timeoutMs.
            // If the timeout occurs, SocketTimeoutException is thrown.
            int timeoutMs = 2000;   // 2 seconds
            sock.connect(sockaddr, timeoutMs);
            exists = true;
        } catch(IOException e) {
            // Handle exception
        }
        return exists;
    }


    private boolean executeCommand() {


        System.out.println("executeCommand");
        Runtime runtime = Runtime.getRuntime();
        try {
            Process mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 www.radar118.com");
            int mExitValue = mIpAddrProcess.waitFor();
            System.out.println(" mExitValue " + mExitValue);
            if (mExitValue == 0) {
                return true;
            } else {
                return true;
            }
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
            System.out.println(" Exception:" + ignore);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(" Exception:" + e);
        }
        return true;
    }


}