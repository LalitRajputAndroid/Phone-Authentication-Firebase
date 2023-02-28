package com.example.otpverification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    EditText number, email;
    MaterialButton nextbtn, emailnext;
    CountryCodePicker ccp;

    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = findViewById(R.id.number_ed);
        nextbtn = findViewById(R.id.nextbtn_id);
        ccp = findViewById(R.id.ccp_id);

        ccp.registerCarrierNumberEditText(number);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OTP_verificationActivity2.class);
                intent.putExtra("mobile", ccp.getFullNumberWithPlus().replace(" ", ""));
                startActivity(intent);
                number.setText("");
                finish();
            }
        });


//        Email verification

//        email = findViewById(R.id.email_ed);
//        emailnext = findViewById(R.id.email_nextbtn_id);
//        emailresend = findViewById(R.id.email_resend_id);
//
//        String Email = email.getText().toString();
//
//        emailnext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Random random = new Random();
//                code = random.nextInt(254621) + 552244;
//                String Url = "";
//
//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(MainActivity.this, "done" + response, Toast.LENGTH_SHORT).show();
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Toast.makeText(MainActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//                    @Nullable
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> map = new HashMap<>();
//                        map.put("email", Email);
//                        map.put("code", String.valueOf(code));
//
//                        return map;
//                    }
//                };
//                requestQueue.add(stringRequest);
//            }
//        });
    }
}