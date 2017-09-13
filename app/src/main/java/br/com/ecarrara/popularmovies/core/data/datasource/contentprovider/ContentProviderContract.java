package br.com.ecarrara.popularmovies.core.data.datasource.contentprovider;

import android.net.Uri;

public final class ContentProviderContract {

    private ContentProviderContract() { /* Must not be instantiated */ }

    public static final String CONTENT_AUTHORITY = "br.com.ecarrara.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final long ENTRY_NOT_PERSISTED_ID = -1L;

}
