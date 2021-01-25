package com.example.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = null;
    private int uid;
    private EditText phone;
    private EditText body;
    private MaterialButton btn;
    private TextView phonex;
    private TextView bod;
    private TextView mark;
    private JSONObject inp1;
    private JSONObject inp2;
    private RequestQueue RQ;
    private JsonObjectRequest JR;
    private String urlsend ="http://192.168.43.142:3000/sendsms";
    private String urlget ="http://192.168.43.142:3000/smsget";
    private String urlsent ="http://192.168.43.142:3000/sent";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone=findViewById(R.id.phone);
        body=findViewById(R.id.body);
        btn= findViewById(R.id.btn);
        phonex=(TextView)findViewById(R.id.number);
        bod=(TextView)findViewById(R.id.bod);
        mark=findViewById(R.id.mark);

        inp1 = new JSONObject();

        RQ = Volley.newRequestQueue(this);

        btn.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {

               String ph=  phone.getText().toString();
               String bd=  body.getText().toString();
               try {
                   inp1.put("phone",ph);
               } catch (JSONException e) {
                   e.printStackTrace();
               }
               try {
                   inp1.put("smsbody",bd);
               } catch (JSONException e) {
                   e.printStackTrace();
               }
                postrequest();
            }
        });



        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getrequest();
                handler.postDelayed(this,3000);
            }
        };handler.postDelayed(runnable,3000);

    }

    private void setmarked(int id) {
        JSONObject obj= new JSONObject();
        try {
            obj.put("uid",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlsent, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Toast.makeText(MainActivity.this,"MARKED SMS AS SENT", Toast.LENGTH_SHORT).show();
        RQ.add(request);
    }

    private void getrequest() {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlget, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("result");
                            JSONObject values = jsonArray.getJSONObject(0);
                            int id = values.getInt("id");
                            String p = values.getString("phone");
                            String b = values.getString("body");
                            phonex.setText(p);
                            bod.setText(b);
                            setmarked(id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"NO MORE UNSENT SMS", Toast.LENGTH_SHORT).show();
            }
        });
        RQ.add(request);
    }

    private void postrequest() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlsend, inp1,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Toast.makeText(MainActivity.this,"SMS SENT SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        RQ.add(request);
    }



}