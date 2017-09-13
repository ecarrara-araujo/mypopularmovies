package br.com.ecarrara.popularmovies.core.networking.rest;

public class RestImageUrlBuilder {

    public static String buildBackdropUrl(String imagePath) {
        return build(imagePath, RestConfigs.IMAGE_SIZE_BACKDROP);
    }

    public static String buildPosterUrl(String imagePath) {
        return build(imagePath, RestConfigs.IMAGE_SIZE_POSTER);
    }

    private static String build(String imagePath, String imageSize) {
        return RestConfigs.SERVER_IMAGE_URL + imageSize + imagePath;
    }

}
