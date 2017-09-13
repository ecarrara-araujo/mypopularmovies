package br.com.ecarrara.popularmovies.movies.presentation.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.ecarrara.popularmovies.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieSynopsisFragment extends Fragment {

    @BindView(R.id.movie_detail_synopsis_text_view)
    TextView movieSynopsisTextView;

    private static final String ARGUMENT_MOVIE_SYNOPSIS = "movie_synopsis";

    public static MovieSynopsisFragment newInstance(String synopsis) {
        MovieSynopsisFragment movieSynopsisFragment = new MovieSynopsisFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_MOVIE_SYNOPSIS, synopsis);
        movieSynopsisFragment.setArguments(args);
        return movieSynopsisFragment;
    }

    private String movieSynopsis;

    public MovieSynopsisFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieSynopsis = getArguments().getString(ARGUMENT_MOVIE_SYNOPSIS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.movie_details_synopsis_fragment, container, false);
        ButterKnife.bind(this, inflatedView);

        movieSynopsisTextView.setText(movieSynopsis);

        return inflatedView;
    }

}
