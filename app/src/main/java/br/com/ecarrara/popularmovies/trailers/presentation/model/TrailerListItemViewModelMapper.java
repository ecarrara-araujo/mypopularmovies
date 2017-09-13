package br.com.ecarrara.popularmovies.trailers.presentation.model;

import java.util.ArrayList;
import java.util.List;

import br.com.ecarrara.popularmovies.trailers.domain.entity.Trailer;

public class TrailerListItemViewModelMapper {

    private static TrailerListItemViewModel transformFrom(Trailer trailer) {
        return TrailerListItemViewModel.create(trailer.key(), trailer.name(), trailer.site());
    }

    public static List<TrailerListItemViewModel> transformFrom(List<Trailer> trailers) {
        List<TrailerListItemViewModel> transformedTrailers = new ArrayList<>(trailers.size());
        for(Trailer trailer : trailers) {
            transformedTrailers.add(TrailerListItemViewModel.create(
                    trailer.key(), trailer.name(), trailer.site()
            ));
        }
        return transformedTrailers;
    }

}
