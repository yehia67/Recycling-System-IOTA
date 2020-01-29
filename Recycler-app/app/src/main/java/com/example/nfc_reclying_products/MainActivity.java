package com.example.nfc_reclying_products;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    Constants base_url = new Constants();
    static final int request_code = 1;
    EditText address;
    Button change_address;
    TextView current_address;
    Button history_button;
    TextView mTextView;
    SaveReceivingAddress manage_address = new SaveReceivingAddress();
    Button my_items_button;
    Button scan_nfc_tag_button;
    Button set_address;
    public  String root;
    public boolean flag = true ;
    /* access modifiers changed from: protected */
    public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        if (_requestCode == 1) {
            try {
                String scannedItem = _data.getStringExtra(ScanNfcTag.key);
                addOwner(scannedItem);
                this.mTextView.setText(scannedItem);
                Context applicationContext = getApplicationContext();
                StringBuilder sb = new StringBuilder();
                sb.append("product address is ");
                sb.append(scannedItem);
                Toast.makeText(applicationContext, sb.toString(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "You scanned nothing", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        address = (EditText) findViewById(R.id.address);
        set_address = (Button) findViewById(R.id.setAddress);
        change_address = (Button) findViewById(R.id.changeAdress);
        change_address.setVisibility(View.INVISIBLE);
        mTextView = (TextView) findViewById(R.id.textView_explanation);
        mTextView.setText("item data will appear here when you start scanning");
        scan_nfc_tag_button = (Button) findViewById(R.id.scanNfcTagButton);
        history_button = (Button) findViewById(R.id.historyButton);
        my_items_button = (Button) findViewById(R.id.myItemsButton);
        getCurrentAddress();
        Intent main_activity_get_intent1 = getIntent();
        final Intent scan_nfc_tag_intent_go = new Intent(this, ScanNfcTag.class);
        //------------------------------------------------------------------------------------------
        RequestQueue request_queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest init_request = new StringRequest(Request.Method.GET,base_url.BASE_URL+"init",new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"init response "+response.toString(), LENGTH_LONG).show();
                root = response ;
                root = root.replaceAll("\"","");
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("root", MODE_PRIVATE).edit() ;
                editor.putString("root", root);
                editor.apply();
                Toast.makeText(getApplicationContext(),"root stored successfully!", LENGTH_LONG);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), LENGTH_LONG).show();
            }
        });
        request_queue.add(init_request);
        //------------------------------------------------------------------------------------------

        change_address.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.address.setEnabled(true);
                MainActivity.this.set_address.setVisibility(View.VISIBLE);
                MainActivity.this.change_address.setVisibility(View.INVISIBLE);
            }
        });
        this.scan_nfc_tag_button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivityForResult(scan_nfc_tag_intent_go, request_code);
            }
        });
    }

    public void getCurrentAddress() {
        current_address = (TextView) findViewById(R.id.defaultReceivingAddress);
        String str = "404 address";
        SharedPreferences prefs = getSharedPreferences("Local Address", MODE_PRIVATE);
        String get_address = prefs.getString("address", "404 address");
        if (!get_address.equals(str)) {
            change_address.setVisibility(View.VISIBLE);
            change_address.setText("Change Address");
            address.setEnabled(false);
            set_address.setVisibility(View.INVISIBLE);
            scan_nfc_tag_button.setVisibility(View.VISIBLE);
            history_button.setVisibility(View.VISIBLE);
            my_items_button.setVisibility(View.VISIBLE);
            TextView text_view = this.current_address;
            StringBuilder sb = new StringBuilder();
            sb.append("Your current address is ");
            sb.append(get_address);
            text_view.setText(sb.toString());
            return;
        }
        this.set_address.setText("Set Adress");
        this.scan_nfc_tag_button.setVisibility(View.INVISIBLE);
        this.history_button.setVisibility(View.INVISIBLE);
        this.my_items_button.setVisibility(View.INVISIBLE);
    }

    public void storeAddress(View _view) {
        if (manage_address.checkAddress(address.getText().toString())) {
            try {
                manage_address.storeAddress(this.address.getText().toString(), getApplicationContext());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Address ", Toast.LENGTH_SHORT).show();
        }
        getCurrentAddress();
    }
    //----------------------------------------------------------------------------------------------------------------
    public void addOwner(String _productAdress){
        SharedPreferences shared_preferences = getSharedPreferences("Local Address", Context.MODE_PRIVATE);
        String owner_adderss = shared_preferences.getString("address", "404 address");
        if(!(owner_adderss == "404 address")){
            try {
                RequestQueue request_queue = Volley.newRequestQueue(getApplicationContext());
                JSONObject json_body = new JSONObject();
                json_body.put("root",root);
                json_body.put("productAddress",_productAdress);
                json_body.put("ownerAddress",owner_adderss);
                final String request_body = json_body.toString();

                StringRequest string_request = new StringRequest(Request.Method.POST, base_url.BASE_URL+"addOwner", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"response is "+response, LENGTH_LONG).show();
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
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
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
             Toast.makeText(getApplicationContext(), owner_adderss , LENGTH_SHORT).show();
        }

    }
    //-----------------------------------------------------------------------------------------------------------

}
