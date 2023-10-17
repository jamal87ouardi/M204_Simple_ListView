package com.example.m204_simple_listview_from_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    JSONArray sa = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url="https://run.mocky.io/v3/d9293f76-e064-4731-ba9f-dcef017cca1a";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest joRequest=new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        sa = response;

                        ArrayList<String> listFilms = new ArrayList<String>();

                        try {
                            for (int i = 0; i < sa.length(); i++) {


                                String name = sa.getJSONObject(i).getString("name");
                                listFilms.add(name);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        ListView lv = findViewById(R.id.lv);

                        ArrayAdapter<String> AA = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_list_item_1,listFilms);
                        lv.setAdapter(AA);

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                try {

                                    TextView textPrice = findViewById(R.id.price);
                                    Switch swTra = findViewById(R.id.trans);
                                    ImageView img = findViewById(R.id.imageView);

                                    Double p = sa.getJSONObject(i).getDouble("price");
                                    Boolean isTranstated = sa.getJSONObject(i).getBoolean("traduction");
                                    String url = sa.getJSONObject(i).getString("image");

                                    textPrice.setText(String.valueOf(p));

                                    swTra.setChecked(isTranstated);

                                    Picasso.get().load(url).into(img);


                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }




                            }
                        });



                    }},

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }


                });
        requestQueue.add(joRequest);




    }
}