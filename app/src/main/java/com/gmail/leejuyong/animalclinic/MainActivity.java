package com.gmail.leejuyong.animalclinic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
// loopj 사용을 위한 import
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ArrayList<Hospital_Info> hostital_list;
    String Url = "http://openapi.sb.go.kr:8088/4d706b6d796a756e32324952545656/json/SbAnimalHospital/1/100";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hostital_list = new ArrayList<>();

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
                        if(pharmacies.getJSONObject(i).getString("TRD_STATE_GBN_CTN").equals("정상"))
                        {
                            if(pharmacies.getJSONObject(i).getString("SITE_TEL").contains("02-"))
                            {
                                Hospital_Info info = new Hospital_Info(pharmacies.getJSONObject(i).getString("WRKP_NM"), pharmacies.getJSONObject(i).getString("SITE_ADDR"), pharmacies.getJSONObject(i).getString("SITE_TEL"));
                                hostital_list.add(info);
                            }
                            else
                            {
                                if(pharmacies.getJSONObject(i).getString("SITE_TEL").equals(""))
                                {
                                    Hospital_Info info = new Hospital_Info(pharmacies.getJSONObject(i).getString("WRKP_NM"), pharmacies.getJSONObject(i).getString("SITE_ADDR"), "없음");
                                    hostital_list.add(info);
                                }
                                else
                                {
                                    Hospital_Info info = new Hospital_Info(pharmacies.getJSONObject(i).getString("WRKP_NM"), pharmacies.getJSONObject(i).getString("SITE_ADDR"), "02-" + pharmacies.getJSONObject(i).getString("SITE_TEL"));
                                    hostital_list.add(info);
                                }
                            }

                        }
                    }

                    //  정보를 로그에 출력하는 기능
                    for (int num=0; num < hostital_list.size(); num++)
                    {
                        Log.d("Test", hostital_list.get(num).hospital_Name);
                        Log.d("Test", hostital_list.get(num).getHospital_Address);
                        Log.d("Test", hostital_list.get(num).getHospital_Phone);
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
