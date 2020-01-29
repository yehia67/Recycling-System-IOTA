package com.example.nfc_reclying_products;

import android.content.SharedPreferences;
import android.content.*;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_LONG;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

public class SaveReceivingAddress extends AppCompatActivity {
    public boolean checkAddress(String _address) {
        if ((_address.codePointCount(0,_address.length()) == 90 || _address.codePointCount(0,_address.length()) == 81)) {
            for (int i = 0; i < _address.length(); i++) {
                if (_address.indexOf(i) != '9' && isLowerCase(_address.indexOf(i))) {
                    return false;
                }
            }
            return true;
        }

        else{
            return false;
        }
    }

  public  void storeAddress( String _address,Context _context){
      try {
            SharedPreferences.Editor editor = _context.getSharedPreferences("Local Address", MODE_PRIVATE).edit();
            editor.putString("address", _address);
            editor.apply();
            Toast.makeText(_context,"address stored successfully!", LENGTH_LONG);

      }catch (Exception e){
            Toast.makeText(_context,"Something went wrong address didn't stored successfully", LENGTH_LONG);

      }
  }


}
