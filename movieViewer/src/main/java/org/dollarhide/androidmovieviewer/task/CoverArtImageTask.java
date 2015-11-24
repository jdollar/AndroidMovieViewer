package org.dollarhide.androidmovieviewer.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import org.dollarhide.androidmovieviewer.model.Configuration;
import org.dollarhide.androidmovieviewer.service.ImageService;
import org.dollarhide.androidmovieviewer.util.ConfigurationManager;
import org.dollarhide.androidmovieviewer.util.LoggingUtil;

import java.net.URL;
import java.util.Iterator;

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
                    String bitmapUrl = imageService.getBitmapUrl(baseContext, movieId);
                    imageToDisplay = coverArtExecute(baseContext, bitmapUrl);
                } catch (Exception e) {
                    LoggingUtil.logException(TAG, e);
                }
            } else {
                LoggingUtil.logDebug(TAG, "Params are not expected for Cover Art Retrieval [ImageView, String]");
            }
        }

        return imageToDisplay;
    }

    @Override
    protected void onPostExecute(Object imageToDisplay) {
        posterView.setImageBitmap((Bitmap) imageToDisplay);
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
            String posterSize = DEFAULT_POSTER_SIZE;

            if (!configuration.getPosterSizes().contains(DEFAULT_POSTER_SIZE)) {
                //if the default poster size isn't available use the first available size
                for (Iterator<String> posterIt = configuration.getPosterSizes().iterator(); posterIt.hasNext();) {
                    posterSize = posterIt.next();
                    break;
                }
            }

            //retrieve bitmap from the specified url. Poster size required.
            URL imageUrl = new URL(configuration.getBaseImageUrl() + posterSize + imagePath);
            imageToDisplay = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
        } catch (Exception e) {
            LoggingUtil.logException(TAG, e);
        }

        return imageToDisplay;
    }

    private boolean checkParamTypes(Object[] params) {
        return params[0] instanceof Context
                && params[1] instanceof ImageView
                && params[2] instanceof String;
    }
}
