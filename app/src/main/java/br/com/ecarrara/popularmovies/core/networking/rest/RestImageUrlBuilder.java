package br.com.ecarrara.popularmovies.core.networking.rest;

public class RestImageUrlBuilder {

    public static String build(String imagePath) {
        return RestConfigs.SERVER_IMAGE_URL +
                RestConfigs.IMAGE_SIZE +
                imagePath;
    }

}
