package br.com.ecarrara.popularmovies.trailers.presentation.view;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.trailers.presentation.model.TrailerListItemViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrailersListAdapter extends RecyclerView.Adapter<TrailersListAdapter.ViewHolder> {

    private List<TrailerListItemViewModel> trailerListItemViewModelList;
    private Context parentContext;
    private TrailerSelectedListener trailerSelectedListener;
    private TrailerSharedListener trailerSharedListener;

    interface TrailerSelectedListener {
        void onTrailerSelected(String site, String key);
    }

    interface TrailerSharedListener {
        void onTrailerShared(String site, String key);
    }

    public TrailersListAdapter(Context parentContext,
                               TrailerSelectedListener trailerSelectedListener,
                               TrailerSharedListener trailerSharedListener) {
        this.parentContext = parentContext;
        this.trailerSelectedListener = trailerSelectedListener;
        this.trailerSharedListener = trailerSharedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_list_item, parent, shouldAttachToParentImmediately);
        return new TrailersListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TrailerListItemViewModel viewItemToBind =  trailerListItemViewModelList.get(position);
        holder.trailerName.setText(viewItemToBind.name());
    }

    @Override
    public int getItemCount() {
        return trailerListItemViewModelList.size();
    }

    public void setTrailerListItemViewModelList(
            List<TrailerListItemViewModel> trailerListItemViewModelList) {
        this.trailerListItemViewModelList = trailerListItemViewModelList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.trailers_trailer_name_text_view) TextView trailerName;
        @BindView(R.id.trailers_play_button) ImageButton playButton;
        @BindView(R.id.trailers_share_button) ImageButton shareButton;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.trailers_play_button)
        public void playTrailer(View v) {
            final TrailerListItemViewModel clickedItem = trailerListItemViewModelList.get(
                    getAdapterPosition()
            );
            TrailersListAdapter.this.trailerSelectedListener.onTrailerSelected(
                    clickedItem.site(), clickedItem.key()
            );
        }

        @OnClick(R.id.trailers_share_button)
        public void shareTrailer(View v) {
            final TrailerListItemViewModel clickedItem = trailerListItemViewModelList.get(
                    getAdapterPosition()
            );
            TrailersListAdapter.this.trailerSharedListener.onTrailerShared(
                    clickedItem.site(), clickedItem.key()
            );
        }
    }

}
