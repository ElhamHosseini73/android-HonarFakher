package ir.rayapars.honarfakher.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import java.util.ArrayList;
import java.util.List;

import ir.rayapars.honarfakher.R;
import ir.rayapars.honarfakher.classes.ProductDetails;
import ir.rayapars.honarfakher.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    public static List<ProductDetails> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

           FragmentTransaction transactionMain = getSupportFragmentManager().beginTransaction();
            transactionMain.add(R.id.frame_layout, new MainFragment()).commit();
        }

        //زرین پال
        Uri data = getIntent().getData();

        ZarinPal.getPurchase(this).verificationPayment(data, new OnCallbackVerificationPaymentListener() {

            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {

            }
        });
    }

    //زرین پال private was changed to public
    public void myPayment(long amount) {

        ZarinPal purchase = ZarinPal.getPurchase(this);
        PaymentRequest payment = ZarinPal.getPaymentRequest();
        payment.setMerchantID("7658e0fa-e969-11e7-9f7d-005056a205be");
        payment.setAmount(amount);
        payment.setDescription("Online Payment");
        payment.setCallbackURL("return://zarinpalpayment");

        purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {
                if (status == 100)
                    startActivity(intent);
                else
                    Toast.makeText(MainActivity.this, "خطا در ایجاد درخواست پرداخت", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {*/
        super.onBackPressed();
        // }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
