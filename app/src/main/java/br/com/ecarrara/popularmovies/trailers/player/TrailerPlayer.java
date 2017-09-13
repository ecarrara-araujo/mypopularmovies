package br.com.ecarrara.popularmovies.trailers.player;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.trailers.utils.YoutubeUriBuilder;

public class TrailerPlayer {

    private static final String TRAILER_SITE_YOUTUBE = "YouTube";

    public static void playTrailer(Context context, String site, String trailerKey) {
        Uri trailerUri = null;
        switch (site) {
            case TRAILER_SITE_YOUTUBE:
                trailerUri = YoutubeUriBuilder.buildYoutubeUri(trailerKey);
                break;
            default:
                throw new RuntimeException("Trailer site not supported.");
        }
        playTrailer(context, trailerUri);
    }

    private static void playTrailer(Context context, Uri trailerUri) {
        Intent playTrailerIntent = new Intent(Intent.ACTION_VIEW, trailerUri);
        final String title = context.getString(R.string.trailer_player_chooser_title);
        context.startActivity(Intent.createChooser(playTrailerIntent, title));
    }

}
