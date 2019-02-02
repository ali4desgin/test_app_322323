package com.example.ali4desgin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bigkoo.pickerview.MyOptionsPickerView;
import com.example.ali4desgin.myapplication.Api.Links;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    private Button ToRegisterStep2;
    EditText usernameID,passwordID,emailID,confirmpasswordID,phoneID;
    TextView cityTxt;
    String city = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ToRegisterStep2 = (Button) findViewById(R.id.ToRegisterStep2);


        usernameID = (EditText) findViewById(R.id.carnameID2);
        emailID = (EditText) findViewById(R.id.emailID);
        confirmpasswordID = (EditText) findViewById(R.id.confirmpasswordID2);
        passwordID = (EditText) findViewById(R.id.passwordID2);
        phoneID = (EditText) findViewById(R.id.phoneID);

        cityTxt = (TextView) findViewById(R.id.cityTxt);



        final MyOptionsPickerView singlePicker = new MyOptionsPickerView(RegisterActivity.this);
        final ArrayList<String> items = new ArrayList<String>();
        items.add("الرياض");
        items.add("المدينة");
        items.add("مكة");
        items.add("جدة");
        singlePicker.setPicker(items);
        singlePicker.setTitle("Choose city");
        singlePicker.setCyclic(false);
        singlePicker.setSelectOptions(0);
        singlePicker.setOnoptionsSelectListener(new MyOptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                cityTxt.setText(items.get(options1));
                city = items.get(options1);
                //  singleTVOptions.setText("Single Picker " + items.get(options1));
                //Toast.makeText(RegisterActivity.this, "" + items.get(options1), Toast.LENGTH_SHORT).show();
                //vMasker.setVisibility(View.GONE);
            }
        });




        cityTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singlePicker.show();
            }
        });



        ToRegisterStep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regsiter();
            }
        });
    }






    public void  regsiter(){

        String username = usernameID.getText().toString();
        String password = passwordID.getText().toString();
        String confirmpassword = confirmpasswordID.getText().toString();
        String phone = phoneID.getText().toString();
        String email = emailID.getText().toString();
        Toast.makeText(RegisterActivity.this,"start",Toast.LENGTH_LONG).show();


        if(username.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || phone.isEmpty() || email.isEmpty() || city.isEmpty()){
            Toast.makeText(RegisterActivity.this,"All field should be not empty",Toast.LENGTH_LONG).show();

        }else {


            if(!password.matches(confirmpassword)){
                Toast.makeText(RegisterActivity.this,"password should match confirm password",Toast.LENGTH_LONG).show();
            }else{
                register_progress(email,username,phone,city,password);

            }




        }


    }




    public void register_progress(final String email,final String username,final String phone,final String city, final String password){

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,Links.register_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("res")){

                                String id = jsonObject.getString("user_id");

                                Intent intent = new Intent(RegisterActivity.this,RegisterStep2.class);
                                intent.putExtra("user_id",id);
                                startActivity(intent);


                            }else{


                                Toast.makeText(RegisterActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

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
                map.put("email",email);
                map.put("password",password);
                map.put("username",username);
                map.put("city",city);
                map.put("phone",phone);
                map.put("step","1");
                return map;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
