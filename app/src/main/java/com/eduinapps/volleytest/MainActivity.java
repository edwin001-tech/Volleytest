package com.eduinapps.volleytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText username, phone, identification, password, password2, email;
    private Button register;
    private ProgressBar loadingPB;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing our views
        username = findViewById(R.id.username);
        email =findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        identification = findViewById(R.id.idnumber);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.confirmpassword);
        register = findViewById(R.id.register);

        loadingPB = findViewById(R.id.idLoadingPB);

        // adding on click listener to our button.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (username.getText().toString().isEmpty() && email.getText().toString().isEmpty() && phone.getText().toString().isEmpty() && identification.getText().toString().isEmpty()
                && password.getText().toString().isEmpty() && password2.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Empty fields not allowed", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postDataUsingVolley(username.getText().toString(), email.getText().toString(), phone.getText().toString(), identification.getText().toString(),
                        password.getText().toString(), password2.getText().toString());
            }
        });
    }

    private void postDataUsingVolley(String username, String email, String phone, String identification, String password, String password2) {
        // our django endpoint
        String url = " http://7f20-41-81-94-210.ngrok.io/register";
        loadingPB.setVisibility(View.VISIBLE);

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                // and setting data to edit text as empty
                loadingPB.setVisibility(View.GONE);


                // on below line we are displaying a success toast message.
                Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
//                try {
//                    // on below line we are passing our response
//                    // to json object to extract data from it.
//                    JSONObject respObj = new JSONObject(response);
//
//                    // below are the strings which we
//                    // extract from our json object.
//                    String name = respObj.getString("username");
//                    String job = respObj.getString("phone");
//
//                    // on below line we are setting this string s to our text view.
//                    responseTV.setText("Name : " + name + "\n" + "Job : " + job);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(MainActivity.this, "Error = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("username", username);
                params.put("phone", phone);
                params.put("identification", identification);
                params.put("password", password);
                params.put("password2", password2);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
}