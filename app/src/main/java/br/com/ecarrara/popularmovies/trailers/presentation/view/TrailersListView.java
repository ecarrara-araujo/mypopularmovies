package br.com.ecarrara.popularmovies.trailers.presentation.view;

import java.util.List;

import br.com.ecarrara.popularmovies.core.presentation.LoadDataView;
import br.com.ecarrara.popularmovies.trailers.presentation.model.TrailerListItemViewModel;

public interface TrailersListView extends LoadDataView {
    void displayTrailersList(List<TrailerListItemViewModel> trailerListItemViewModelList);
    void playTrailer(String site, String key);
}
