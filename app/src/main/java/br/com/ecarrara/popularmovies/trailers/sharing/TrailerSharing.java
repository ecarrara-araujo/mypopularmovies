package br.com.ecarrara.popularmovies.trailers.sharing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.trailers.utils.YoutubeUriBuilder;

public class TrailerSharing {

    private static final String TRAILER_SITE_YOUTUBE = "YouTube";

    public static void shareTrailer(Context context, String site, String trailerKey) {
        Uri trailerUri = null;
        switch (site) {
            case TRAILER_SITE_YOUTUBE:
                trailerUri = YoutubeUriBuilder.buildYoutubeUri(trailerKey);
                break;
            default:
                throw new RuntimeException("Trailer site not supported.");
        }
        shareTrailer(context, trailerUri);
    }

    private static void shareTrailer(Context context, Uri trailerUri) {
        final String title = context.getString(R.string.trailer_sharing_chooser_title);
        final String message = context.getString(R.string.trailer_sharing_message);
        Intent shareTrailerIntent = new Intent(android.content.Intent.ACTION_SEND, trailerUri);
        shareTrailerIntent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(shareTrailerIntent, title));
    }

}
