package com.hpt.lottery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CheckoutPage extends AppCompatActivity {
    private FirebaseFirestore db;
    final int UPI_PAYMENT = 0;

    private int counter;
    private TextView qtytv;
    private TextView pricetv;
    private int intoprice;
    private int intprice;
    private String name = "Lotto";
    private String email = "abc@xyz.com";


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.increment:
                    incrCounter();
                    break;
                case R.id.decrement:
                    decrCounter();
                    break;

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_checkout_page);
        db = FirebaseFirestore.getInstance();
        pricetv = findViewById(R.id.price);
        TextView participantstv = findViewById(R.id.participants);
        TextView winnerstv = findViewById(R.id.winners);
        qtytv = findViewById(R.id.quantity);
        TextView winningamttv = findViewById(R.id.winningamount);
        LinearLayout incr = findViewById(R.id.increment);
        LinearLayout decr = findViewById(R.id.decrement);
        Button payupi = findViewById(R.id.upi);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        final DocumentReference documentReference = db.collection("users").document(userID);
        documentReference.addSnapshotListener(CheckoutPage.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                name = documentSnapshot.getString("name");
                email = documentSnapshot.getString("email");
            }
        });
        payupi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startpay();
            }
        });

        pricetv.setText(String.valueOf(getIntent().getIntExtra("price", 10000)));
        participantstv.setText(String.valueOf(getIntent().getIntExtra("participants", 10000)));
        winnerstv.setText(String.valueOf(getIntent().getIntExtra("winno", 10000)));
        String windiff = getIntent().getIntExtra("winstart", 10000) + " - " + getIntent().getIntExtra("winend", 10000);
        winningamttv.setText(windiff);

        intprice = getIntent().getIntExtra("price", 10000);
        incr.setOnClickListener(clickListener);
        decr.setOnClickListener(clickListener);
        initCounter();
    }
    @SuppressLint("SetTextI18n")
    private void initCounter(){
        counter = 1;
        qtytv.setText(counter + "");
    }

    @SuppressLint("SetTextI18n")
    private void incrCounter(){
        counter++;
        qtytv.setText(counter + "");
        intoprice = counter*intprice;
        pricetv.setText(String.valueOf(intoprice));

    }
    @SuppressLint("SetTextI18n")
    private void decrCounter(){
        if(counter > 1 ){
            counter--;
            qtytv.setText(counter + "");
            intoprice = counter*intprice;
            pricetv.setText(String.valueOf(intoprice));
        }
        else {
            Toast.makeText(CheckoutPage.this,"Quantity cannot be less than 1", Toast.LENGTH_SHORT).show();
        }
    }

    public void startpay() {
        intoprice = getIntent().getIntExtra("price", 10000);
        intoprice = counter*intprice;

        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa", "9304895389@ybl")
                        .appendQueryParameter("pn", "Lotto")
//                        .appendQueryParameter("mc", "your-merchant-code")
//                        .appendQueryParameter("tr", "your-transaction-ref-id")
                        .appendQueryParameter("tn", "your-transaction-note")
                        .appendQueryParameter("am", String.valueOf(intoprice))
                        .appendQueryParameter("cu", "INR")
//                        .appendQueryParameter("url", "your-transaction-url")
                        .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
//        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
//        activity.startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);

        Intent chooser = Intent.createChooser(intent, "Pay using");

        if(null != chooser.resolveActivity(getPackageManager())){
            startActivityForResult(chooser, UPI_PAYMENT);
        }
        else {
            Toast.makeText(CheckoutPage.this, "No UPI app found, Please install one to continue", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response "+resultCode );
        if (requestCode == UPI_PAYMENT) {
            if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                if (data != null) {
                    String trxt = data.getStringExtra("response");
                    Log.e("UPI", "onActivityResult: " + trxt);
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList);
                } else {
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            } else {
                //when user simply back without payment
                Log.e("UPI", "onActivityResult: " + "Return data is null");
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");
                upiPaymentDataOperation(dataList);
            }
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(CheckoutPage.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String[] response = str.split("&");
            for (String s : response) {
                String[] equalStr = s.split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(CheckoutPage.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: "+approvalRefNo);
                AddData();
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(CheckoutPage.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);

            }
            else {
                Toast.makeText(CheckoutPage.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);

            }
        } else {
            Log.e("UPI", "Internet issue: ");

            Toast.makeText(CheckoutPage.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(CheckoutPage context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable();
        }
        return false;
    }

    public void AddData(){

            Map<String , String> paymtData = new HashMap<>();
                    paymtData.put("name", name);
                    paymtData.put("email", email);
                    paymtData.put("amountspent", String.valueOf(intoprice));
                    paymtData.put("quantity", String.valueOf(counter));

                    db.collection("sales").document(email).set(paymtData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                        Toast.makeText(CheckoutPage.this, "Payment Successful: ", Toast.LENGTH_SHORT).show();
                        }
                        });

    }
    }

