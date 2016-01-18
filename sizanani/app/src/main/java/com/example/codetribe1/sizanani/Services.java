package com.example.codetribe1.sizanani;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.codetribe1.sizanani.adaptors.CategoriesAdaptor;
import com.example.codetribe1.sizanani.adaptors.ServicesAdaptor;
import com.example.codetribe1.sizanani.dto.CategoryDTO;
import com.example.codetribe1.sizanani.dto.ServicesDTO;
import com.example.codetribe1.unischool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by geoffrey on 1/17/16.
 */
public class Services extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    TextView SEV_CAT_name;
    TextView SEV_CAT_description;

    Context ctx;
    private String TAG = Services.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ServicesAdaptor adapter;
    private List<ServicesDTO> services;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_list);

        SEV_CAT_name = (TextView) findViewById(R.id.SEV_CAT_name);
        SEV_CAT_description = (TextView) findViewById(R.id.SEV_CAT_description);

        //set from intent
         b = this.getIntent().getBundleExtra("CategoryBundle");
        SEV_CAT_name.setText(b.getString("cat_name"));
        SEV_CAT_description.setText(b.getString("description"));


        listView = (ListView) findViewById(R.id.SEV_listView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SEV_swipe_refresh_layout);

        services = new ArrayList<>();
        ctx = getApplicationContext();
        adapter = new ServicesAdaptor(ctx,services);
        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        fetchServices();
                                    }
                                }
        );
    }



    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        fetchServices();
    }

    public void fetchServices(){
        String tag_string_req = "req_services";
        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        //string request--------------------
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String message = jsonResponse.getString("message");

                    if( jsonResponse.getBoolean("success") ){
                        JSONObject data = new JSONObject(response);
                        JSONArray users = data.getJSONArray("user");
                        for (int i = 0; i < users.length(); i++) {
                            JSONObject user = users.getJSONObject(i);
                            ServicesDTO N = new ServicesDTO();
                            N.setService_ID(user.getInt("service_ID"));
                            N.setDescription(user.getString("firstname"));
                            N.setSurname(user.getString("surname"));
                            N.setDescription(user.getString("description"));
                            N.setLocation(user.getString("location"));
                            N.setType(user.getString("type"));
                            services.add(N);
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "0 failed " + message, Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(), "1 failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                adapter.notifyDataSetChanged();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"2 failed "+error.getMessage(), Toast.LENGTH_LONG).show();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("method", "service_SEL");
                params.put("type", b.getString("cat_type"));
                return params;
            }};

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);

        //--------------------handling the onclick of a button------------------------------------------------
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CategoryDTO item = (CategoryDTO) adapter.getItem(position);
//
//                Intent intent = new Intent(getApplicationContext(), Services.class);
//
//                Bundle b = new Bundle();
//                b.putString("cat_name", item.getCat_name());
//                b.putString("description", item.getDescription());
//                b.putString("cat_type", item.getCat_type());
//                b.putInt("category_ID", item.getCategory_ID());
//                intent.putExtra("CategoryBundle", b);
//                startActivity(intent);
//            }
//        });
    }
}
