package org.dollarhide.androidmovieviewer.util;

import android.content.Context;
import org.dollarhide.androidmovieviewer.model.Configuration;
import org.dollarhide.androidmovieviewer.service.ConfigurationService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class ConfigurationManager {
    private static final String TAG = "ConfigurationManager";

    private Configuration configuration;

    private ConfigurationService configurationService;

    public ConfigurationManager() {
        configurationService = new ConfigurationService();
    }

    public Configuration getConfiguration(Context baseContext) {
        if (configuration == null) {
            configuration = populateConfiguration(baseContext);
        }

        return configuration;
    }

    public Configuration populateConfiguration(Context baseContext) {
        Configuration newConfiguration = new Configuration();

        try {
            JSONObject jsonObject = configurationService.getConfiguration(baseContext);

            //Main object in json response is images
            JSONObject imagesObject = jsonObject.getJSONObject("images");

            //base url is url used for image retrieval
            newConfiguration.setBaseImageUrl(imagesObject.getString("base_url"));

            //populate possible poster sizes from json object
            JSONArray posterSizesJson = imagesObject.getJSONArray("poster_sizes");
            Set<String> posterSizes = new HashSet<String>();
            for(int i = 0; i < posterSizesJson.length(); i++) {
                if (posterSizesJson.get(i) != null) {
                    posterSizes.add(posterSizesJson.get(i).toString());
                }
            }

            newConfiguration.setPosterSizes(posterSizes);
        } catch (Exception e) {
            LoggingUtil.logException(TAG, e);
        }

        return newConfiguration;
    }

}
