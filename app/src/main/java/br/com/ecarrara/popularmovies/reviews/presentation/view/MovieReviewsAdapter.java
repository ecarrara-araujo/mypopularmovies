package br.com.ecarrara.popularmovies.reviews.presentation.view;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.reviews.presentation.model.MovieReviewListItemViewModel;
import br.com.ecarrara.popularmovies.trailers.presentation.view.TrailersListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder> {

    private List<MovieReviewListItemViewModel> movieReviewsListItemViewModelsList;
    private MovieReviewSelectedListener movieReviewSelectedListener;
    private Context parentContext;

    interface MovieReviewSelectedListener {
        void onMovieReviewSelected(int reviewIndex);
    }

    public MovieReviewsAdapter(Context parentContext,
                               MovieReviewSelectedListener movieReviewSelectedListener) {
        this.parentContext = parentContext;
        this.movieReviewSelectedListener = movieReviewSelectedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_review_list_item, parent, shouldAttachToParentImmediately);
        return new MovieReviewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieReviewListItemViewModel viewItemToBind =
                movieReviewsListItemViewModelsList.get(position);
        holder.author.setText(viewItemToBind.author());
        holder.content.setText(viewItemToBind.content());
    }

    @Override
    public int getItemCount() {
        return this.movieReviewsListItemViewModelsList.size();
    }

    public void setMovieReviewsListItemViewModelsList(List<MovieReviewListItemViewModel> movieReviewsListItemViewModelsList) {
        this.movieReviewsListItemViewModelsList = movieReviewsListItemViewModelsList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_view_author) TextView author;
        @BindView(R.id.text_view_content) TextView content;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final MovieReviewListItemViewModel clickedItem = movieReviewsListItemViewModelsList.get(
                    getAdapterPosition()
            );
            MovieReviewsAdapter.this.movieReviewSelectedListener.onMovieReviewSelected(
                    getAdapterPosition()
            );
        }
    }
}
