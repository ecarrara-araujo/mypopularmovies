package br.com.ecarrara.popularmovies.movies.presentation.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.reviews.presentation.view.MovieReviewsFragment;
import br.com.ecarrara.popularmovies.trailers.presentation.view.TrailerListFragment;

public class MovieMoreInfoViewPagerAdapter extends FragmentStatePagerAdapter {

    public static final int PAGE_POSITION_SYNOPSIS = 0;
    public static final int PAGE_POSITION_TRAILERS = 1;
    public static final int PAGE_POSITION_REVIEWS = 2;

    private final int NUMBER_OF_PAGES = 3;

    private Context context;
    private String synopsis;
    private int movieId;

    public MovieMoreInfoViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        this(fragmentManager);
        this.context = context;
    }

    public MovieMoreInfoViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setMovieData(int movieId, String synopsis) {
        this.movieId = movieId;
        this.synopsis = synopsis;
        this.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PAGE_POSITION_SYNOPSIS:
                return MovieSynopsisFragment.newInstance(this.synopsis);
            case PAGE_POSITION_TRAILERS:
                return TrailerListFragment.newInstance(this.movieId);
            case PAGE_POSITION_REVIEWS:
                return MovieReviewsFragment.newInstance(this.movieId);
            default:
                throw new UnsupportedOperationException("Page position not supported: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case PAGE_POSITION_SYNOPSIS:
                return context.getResources().getString(R.string.movie_detail_synopsis);
            case PAGE_POSITION_TRAILERS:
                return context.getResources().getString(R.string.movie_detail_trailers);
            case PAGE_POSITION_REVIEWS:
                return context.getResources().getString(R.string.movie_detail_reviews);
            default:
                throw new UnsupportedOperationException("Page position not supported: " + position);
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }
}
