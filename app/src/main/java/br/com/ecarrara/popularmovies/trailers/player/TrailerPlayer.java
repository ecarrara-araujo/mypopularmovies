package br.com.ecarrara.popularmovies.trailers.player;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import br.com.ecarrara.popularmovies.R;

public class TrailerPlayer {

    private static final String YOUTUBE_BASE_URL = "http://www.youtube.com/watch";
    private static final String YOUTUBE_PARAM_VIDEO_ID = "v";

    private static final String TRAILER_SITE_YOUTUBE = "YouTube";

    public static void playTrailer(Context context, String site, String trailerKey) {
        switch (site) {
            case TRAILER_SITE_YOUTUBE:
                playYoutubeTrailer(context, trailerKey);
                break;
            default:
                throw new RuntimeException("Trailer site not supported.");
        }
    }

    private static void playYoutubeTrailer(Context context, String trailerKey) {
        Intent playTrailerIntent = new Intent(Intent.ACTION_VIEW,
                buildYoutubeUrl(trailerKey));
        final String title = context.getString(R.string.trailer_player_chooser_title);
        context.startActivity(Intent.createChooser(playTrailerIntent, title));
    }

    private static Uri buildYoutubeUrl(String trailerKey) {
        return Uri.parse(YOUTUBE_BASE_URL).buildUpon()
                .appendQueryParameter(YOUTUBE_PARAM_VIDEO_ID, trailerKey)
                .build();
    }

}
