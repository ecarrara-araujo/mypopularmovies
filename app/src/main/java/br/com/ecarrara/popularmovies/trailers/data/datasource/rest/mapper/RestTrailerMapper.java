package br.com.ecarrara.popularmovies.trailers.data.datasource.rest.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.ecarrara.popularmovies.trailers.data.datasource.rest.json.MovieTrailersResult;
import br.com.ecarrara.popularmovies.trailers.domain.entity.Trailer;

public class RestTrailerMapper {

    public static Trailer transformFrom(MovieTrailersResult movieTrailersResult) {
        return new Trailer
                .Builder(movieTrailersResult.id(), movieTrailersResult.name())
                .setCountry(movieTrailersResult.country())
                .setLanguage(movieTrailersResult.language())
                .setKey(movieTrailersResult.key())
                .setSite(movieTrailersResult.site())
                .setSize(movieTrailersResult.size())
                .setType(movieTrailersResult.type())
                .build();
    }

    public static List<Trailer> transformFrom(List<MovieTrailersResult> movieTrailersResultList) {
        List<Trailer> transformedTrailers =  new ArrayList<>(movieTrailersResultList.size());
        for(MovieTrailersResult movieTrailersResult : movieTrailersResultList) {
            transformedTrailers.add(transformFrom(movieTrailersResult));
        }
        return transformedTrailers;
    }

}
