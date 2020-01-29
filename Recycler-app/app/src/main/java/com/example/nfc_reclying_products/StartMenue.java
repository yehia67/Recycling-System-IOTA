package com.example.nfc_reclying_products;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class StartMenue extends AppCompatActivity {
    Button client_app_button ,trash_app_button;
    static final int request_code = 2;
    Constants base_url = new Constants();
    Intent client_app ,trash_app_intent_go;
    public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        if (_requestCode == 2) {
            try {
                String scanned_item = _data.getStringExtra(ScanNfcTag.key);
                StringBuilder sb = new StringBuilder();
                sb.append("product to be recycled address is ");
                sb.append(scanned_item);
                giveReward(scanned_item);
                Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "You scanned nothing", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menue);
        client_app = new Intent(getApplicationContext() ,MainActivity.class);
        trash_app_intent_go = new Intent(getApplicationContext(),ScanForReward.class);
        client_app_button =(Button)findViewById(R.id.clientAppButton);
        trash_app_button = (Button)findViewById(R.id.trashAppButton);
        client_app_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //-------------------------------------------------------------------------------------------------------
                RequestQueue request_queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest test_request = new StringRequest(Request.Method.GET,base_url.BASE_URL+"test",new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),response.toString(), LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(), LENGTH_LONG).show();
                    }
                });
                request_queue.add(test_request);

                //-------------------------------------------------------------------------------------------------------

                startActivity(client_app);
            }
        });
        trash_app_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(trash_app_intent_go ,request_code);
            }
        });

    }
    //----------------------------------------------------------------------------------------------------------------
    public void giveReward(String _productAdress){
        SharedPreferences shared_preferences = getSharedPreferences("root", Context.MODE_PRIVATE);
        String root_address = shared_preferences.getString("root", "404 address");
        if(!(root_address == "404 address")){
            try {
                RequestQueue request_queue = Volley.newRequestQueue(getApplicationContext());
                JSONObject json_body = new JSONObject();
                json_body.put("root",root_address);
                json_body.put("productAddress",_productAdress);
                final String request_body = json_body.toString();

                StringRequest string_request = new StringRequest(Request.Method.POST, base_url.BASE_URL+"giveReward", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"response is "+response, LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Send 1 i. Congrats", LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(), LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return request_body == null ? null : request_body.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", request_body, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String response_string = "";
                        if (response != null) {
                            response_string = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return Response.success(response_string, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                string_request.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                request_queue.add(string_request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), root_address , LENGTH_SHORT).show();
        }

    }
    //-----------------------------------------------------------------------------------------------------------
}
