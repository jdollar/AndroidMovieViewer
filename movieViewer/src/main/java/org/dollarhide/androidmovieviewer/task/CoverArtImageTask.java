package org.dollarhide.androidmovieviewer.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import org.dollarhide.androidmovieviewer.model.Configuration;
import org.dollarhide.androidmovieviewer.service.ImageService;
import org.dollarhide.androidmovieviewer.util.ConfigurationManager;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;

import java.net.URL;
import java.util.Iterator;
import java.util.Set;

public class CoverArtImageTask extends AsyncTask {
    private static final String TAG = "CoverArtImageTask";
    private static final int EXPECTED_PARAM_SIZE = 3;
    private static final String DEFAULT_POSTER_SIZE = "w500";

    private ConfigurationManager configurationManager;
    private ImageService imageService;
    private ImageView posterView;

    public CoverArtImageTask() {
        super();
        this.configurationManager = new ConfigurationManager();
        this.imageService = new ImageService();
    }

    @Override
    protected Bitmap doInBackground(Object[] params) {
        Bitmap imageToDisplay = null;

        if (params != null && params.length == EXPECTED_PARAM_SIZE) {
            if (checkParamTypes(params)) {
                Context baseContext = (Context) params[0];
                posterView = (ImageView) params[1];
                String movieId = (String) params[2];

                try {
                    String imagePath = imageService.getBitmapUrl(baseContext, movieId);
                    imageToDisplay = coverArtExecute(baseContext, imagePath);
                } catch (Exception e) {
                    LoggingUtil.logException(TAG, e);
                }
            } else {
                LoggingUtil.logDebug(TAG, "Params are not expected for Cover Art Retrieval [Context, ImageView, String]");
            }
        }

        return imageToDisplay;
    }

    private Bitmap coverArtExecute(Context baseContext, String imagePath) {
        Bitmap imageToDisplay = null;

        LoggingUtil.logDebug(TAG, "Image Path: " + imagePath);
        //if there are no images for the movie return straight away
        if (imagePath == null) {
            return imageToDisplay;
        }

        try {
            Configuration configuration = configurationManager.getConfiguration(baseContext);
            String posterSize = getPosterSize(configuration.getPosterSizes());

            //retrieve bitmap from the specified url. Poster size required.
            URL imageUrl = new URL(configuration.getBaseImageUrl() + posterSize + imagePath);
            imageToDisplay = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
        } catch (Exception e) {
            LoggingUtil.logException(TAG, e);
        }

        return imageToDisplay;
    }

    private String getPosterSize(Set<String> posterSizes) {
        String posterSize = DEFAULT_POSTER_SIZE;

        //if the default poster size isn't available use the first available size
        if (!posterSizes.contains(DEFAULT_POSTER_SIZE)) {
            for (Iterator<String> posterIt = posterSizes.iterator(); posterIt.hasNext();) {
                return posterIt.next();
            }
        }

        return posterSize;
    }

    private boolean checkParamTypes(Object[] params) {
        return params[0] instanceof Context
                && params[1] instanceof ImageView
                && params[2] instanceof String;
    }
}
