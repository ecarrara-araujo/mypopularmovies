package br.com.ecarrara.popularmovies.trailers.presentation.view;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.trailers.presentation.model.TrailerListItemViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailersListAdapter extends RecyclerView.Adapter<TrailersListAdapter.ViewHolder> {

    private List<TrailerListItemViewModel> trailerListItemViewModelList;
    private Context parentContext;
    private TrailerSelectedListener trailerSelectedListener;

    interface TrailerSelectedListener {
        void onTrailerSelected(String key, String site);
    }

    public TrailersListAdapter(Context parentContext,
                               TrailerSelectedListener trailerSelectedListener) {
        this.parentContext = parentContext;
        this.trailerSelectedListener = trailerSelectedListener;
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_view_trailer_name) TextView trailerName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final TrailerListItemViewModel clickedItem = trailerListItemViewModelList.get(
                    getAdapterPosition()
            );
            TrailersListAdapter.this.trailerSelectedListener.onTrailerSelected(
                    clickedItem.key(), clickedItem.site()
            );
        }
    }

}
