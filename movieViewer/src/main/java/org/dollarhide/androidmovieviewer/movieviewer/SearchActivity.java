package org.dollarhide.androidmovieviewer.movieviewer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class SearchActivity extends ActionBarActivity {

    private static String API_KEY = "51dd2e08c99cf8915af97d62646b87bb";
    private static String KEYWORD_SEARCH = "http://api.themoviedb.org/3/search/keyword?api_key=" + API_KEY;

    private EditText keywordInput;
    private TextView responseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        keywordInput = (EditText) findViewById(R.id.searchText);
        responseView = (TextView) findViewById(R.id.responseTextView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void searchForMovie(View view) {
        String url = KEYWORD_SEARCH + "&query=" + keywordInput.getText();
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,  url, (String) null,  new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray resultList = (JSONArray) response.get("results");
                            for(int i = 0; i < resultList.length(); i++) {
                                JSONObject result = resultList.getJSONObject(i);
                                responseView.setText(responseView.getText() + "\n" + result.get("name"));
                            }
                        } catch(Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        requestQueue.add(jsObjRequest);
    }

    public void clearInputFields(View view) {
        keywordInput.setText("");
    }

}
