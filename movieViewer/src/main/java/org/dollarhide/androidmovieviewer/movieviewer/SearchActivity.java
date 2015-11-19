package org.dollarhide.androidmovieviewer.movieviewer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
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
    private RadioGroup resultRadioGroup;
    private Button selectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        keywordInput = (EditText) findViewById(R.id.searchText);
        responseView = (TextView) findViewById(R.id.responseTextView);
        resultRadioGroup = (RadioGroup) findViewById(R.id.result_radio_group);
        selectButton = (Button) findViewById(R.id.select_button);

        resultRadioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (View.INVISIBLE == selectButton.getVisibility()) {
                            selectButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );
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
        responseView.setText("");

        String url = KEYWORD_SEARCH + "&query=" + keywordInput.getText();
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,  url, (String) null,  new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            resultRadioGroup.removeAllViews();
                            hideSelectButton();

                            responseView.setText(response.toString() + "\n\n");
                            JSONArray resultList = (JSONArray) response.get("results");
                            for(int i = 0; i < resultList.length(); i++) {
                                JSONObject result = resultList.getJSONObject(i);
                                resultRadioGroup.addView(createResultRadioButton(result.get("name").toString()));
                            }
                        } catch(Exception e) {
                            e.printStackTrace();
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
        responseView.setText("");
        hideSelectButton();
        resultRadioGroup.removeAllViews();
    }

    public void viewSelectionInformation(View view) {

    }

    private RadioButton createResultRadioButton(String label) {
        RadioButton resultRadioButton = new RadioButton(this);
        resultRadioButton.setText(label);
        return resultRadioButton;
    }

    private void hideSelectButton() {
        selectButton.setVisibility(View.INVISIBLE);
    }

}
