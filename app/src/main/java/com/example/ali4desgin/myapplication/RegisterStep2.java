package com.example.ali4desgin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ali4desgin.myapplication.Api.Links;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterStep2 extends AppCompatActivity {


    EditText carnameID,carmodelID,carnumberID,issuedateID,expiredateID;
    Button doneButton;
    String user_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step2);


        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        if(user_id.isEmpty()){

            Intent intent2 = new Intent(RegisterStep2.this, RegisterActivity.class);
            intent2.putExtra("user_id",user_id);
            startActivity(intent2);
        }


        doneButton = findViewById(R.id.doneID);


        issuedateID = findViewById(R.id.issuedateID);
        carnumberID = findViewById(R.id.carnumberID2);
        carmodelID = findViewById(R.id.carmodeID2);
        carnameID = findViewById(R.id.carnameID2);
        expiredateID = findViewById(R.id.expiredateID);



        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regsiter();
            }
        });
    }



    public void  regsiter(){

        String issue = issuedateID.getText().toString();
        String expire = expiredateID.getText().toString();
        String number = carnumberID.getText().toString();
        String name = carnameID.getText().toString();
        String model = carmodelID.getText().toString();


        if(issue.isEmpty() || expire.isEmpty() || number.isEmpty() || name.isEmpty() || model.isEmpty() ){
            Toast.makeText(RegisterStep2.this,"All field should be not empty",Toast.LENGTH_LONG).show();

        }else {



            register_progress(name,number,model,issue,expire);






        }


    }




    public void register_progress(final String name,final String number,final String  model,final String issue, final String expire){

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,Links.register_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("res")){

                                Intent intent = new Intent(RegisterStep2.this,HomeActivity.class);
                                startActivity(intent);


                            }else{


                                Toast.makeText(RegisterStep2.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap();
                map.put("car_name",name);
                map.put("car_number",number);
                map.put("car_model", model);
                map.put("issue_date",issue);
                map.put("expire_date",expire);
                map.put("user_id",user_id);
                map.put("step","2");
                return map;
            }
        };

        queue.add(stringRequest);

    }

}
