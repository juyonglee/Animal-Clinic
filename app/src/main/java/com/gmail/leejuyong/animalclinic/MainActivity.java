package com.gmail.leejuyong.animalclinic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
// loopj 사용을 위한 import
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    String Url = "http://openapi.sb.go.kr:8088/4d706b6d796a756e32324952545656/json/SbAnimalHospital/1/100";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Url, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                // JSON이 에러를 발생시킬 수 있으므로
                try
                {

                    // { } 는 object를 의미
                    JSONObject objects = response.getJSONObject("SbAnimalHospital");
                    JSONArray pharmacies = objects.getJSONArray("row");
                    for(int i=0; i<pharmacies.length(); i++)
                    {
                        if(pharmacies.getJSONObject(i).getString("TRD_STATE_GBN_CTN").equals("정상")) {
                            Log.d("Test", pharmacies.getJSONObject(i).getString("WRKP_NM"));
                            Log.d("Test", pharmacies.getJSONObject(i).getString("SITE_ADDR"));
                            Log.d("Test", pharmacies.getJSONObject(i).getString("SITE_TEL"));
                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
