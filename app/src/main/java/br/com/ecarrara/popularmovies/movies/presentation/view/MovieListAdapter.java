package br.com.ecarrara.popularmovies.movies.presentation.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieListItemViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private List<MovieListItemViewModel> movieListItemViewModels;
    private Context parentContext;
    private MovieSelectedListener movieSelectedListener;

    public interface MovieSelectedListener {
        void onMovieSelected(Integer movieId);
    }

    public MovieListAdapter(Context context, MovieSelectedListener movieSelectedListener) {
        this.parentContext = context;
        this.movieSelectedListener = movieSelectedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String moviePath = movieListItemViewModels.get(position).posterPath();
        Picasso.with(parentContext)
                .load(moviePath)
                .noPlaceholder()
                .fit()
                .into(holder.moviePoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.moviePosterLoadingView.setVisibility(GONE);
                    }

                    @Override
                    public void onError() {
                        holder.moviePosterLoadingView.setVisibility(GONE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return movieListItemViewModels == null ? 0 : movieListItemViewModels.size();
    }

    public void setMovieListItemViewModels(List<MovieListItemViewModel> movieListItemViewModels) {
        this.movieListItemViewModels = movieListItemViewModels;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_view_movie_poster) ImageView moviePoster;
        @BindView(R.id.progress_bar_image_loading) ProgressBar moviePosterLoadingView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            moviePoster.setOnClickListener(ViewHolder.this);
        }

        @Override
        public void onClick(View v) {
            MovieListAdapter.this.movieSelectedListener
                    .onMovieSelected(movieListItemViewModels.get(getAdapterPosition()).movieId());
        }
    }

}
