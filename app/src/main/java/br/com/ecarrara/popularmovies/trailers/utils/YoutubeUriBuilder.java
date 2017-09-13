package br.com.ecarrara.popularmovies.trailers.utils;

import android.net.Uri;

public class YoutubeUriBuilder {

    private static final String YOUTUBE_BASE_URL = "http://www.youtube.com/watch";
    private static final String YOUTUBE_PARAM_VIDEO_ID = "v";

    public static Uri buildYoutubeUri(String trailerKey) {
        return Uri.parse(YOUTUBE_BASE_URL).buildUpon()
                .appendQueryParameter(YOUTUBE_PARAM_VIDEO_ID, trailerKey)
                .build();
    }

}
