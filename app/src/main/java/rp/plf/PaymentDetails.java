package rp.plf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {

    TextView id,amont,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        id = findViewById(R.id.txId);
        amont = findViewById(R.id.txAmont);
        status = findViewById(R.id.txStatus);

        Intent intent = getIntent();

        try{
            JSONObject jsonObject= new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmont"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentAmont) {
        try{
            id.setText(response.getString("id"));
            status.setText(response.getString("state"));
            amont.setText(response.getString(String.format("$%s",paymentAmont)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}