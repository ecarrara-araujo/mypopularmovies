package br.com.ecarrara.popularmovies.core.data.datasource.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.ecarrara.popularmovies.favorites.data.datasource.contentprovider.FavoritesContract;

public class SqliteDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mypopularmovies.db";
    private static final int DATABASE_VERSION = 1;

    public SqliteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FavoritesContract.FavoriteMovieEntry.SQL_CREATE_FAVORITE_MOVIE_TABLE);
        db.execSQL(FavoritesContract.FavoriteEntry.SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* Do Nothing for this version */
    }

}
