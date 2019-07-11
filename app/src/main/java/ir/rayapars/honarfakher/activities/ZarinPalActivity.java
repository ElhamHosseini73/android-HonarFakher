package ir.rayapars.honarfakher.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.orm.query.Select;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import java.util.List;

import ir.rayapars.honarfakher.classes.App;
import ir.rayapars.honarfakher.classes.Checkout;
import ir.rayapars.honarfakher.classes.PublicVariable;
import ir.rayapars.honarfakher.classes.Setting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZarinPalActivity extends AppCompatActivity {
    static String order_id;
    static String invoice_number;
    static String amount;
    public static boolean isFirst = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri data = this.getIntent().getData();
        ZarinPal.getPurchase(this).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {

                if (isPaymentSuccess) {
                    Setting s = Select.from(Setting.class).first();
                    verify(s.uid, s.MDU, "1", invoice_number, order_id, refID);
                } else {
                    finish();
                }
            }
        });
        if (isFirst) {
            order_id = getIntent().getStringExtra("order_id");
            invoice_number = getIntent().getStringExtra("invoice_number");
            amount = getIntent().getStringExtra("amount");
            myPayment(Long.parseLong(amount));
            isFirst = false;
        }
    }

    public void myPayment(long amount) {
        ZarinPal purchase = ZarinPal.getPurchase(this);
        PaymentRequest payment = ZarinPal.getPaymentRequest();
        payment.setMerchantID("aca83d34-82a4-11e8-8313-005056a205be");
        payment.setAmount(amount);
        payment.setDescription("Online Payment");
        payment.setCallbackURL("ir.rayapars.baloot://app");

        purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {
                if (status == 100)
                    startActivity(intent);
                else
                    Toast.makeText(ZarinPalActivity.this, "خطا در ایجاد درخواست پرداخت", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<Checkout> checkoutList;

    private void verify(String uid, String MDU, String payment, String iN, String old, String refID) {
        Call<List<Checkout>> call = App.apiInterface.verify(PublicVariable.key, uid, MDU, "1", iN, old, refID);
        call.enqueue(new Callback<List<Checkout>>() {
            @Override
            public void onResponse(Call<List<Checkout>> call, Response<List<Checkout>> response) {
                checkoutList = response.body();
                Toast.makeText(ZarinPalActivity.this, "پرداخت با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<List<Checkout>> call, Throwable t) {
                Toast.makeText(ZarinPalActivity.this, "دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
