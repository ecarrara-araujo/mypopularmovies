package br.com.ecarrara.popularmovies.core.presentation;

/**
 * A Presenter specification that is able to respond to common Android View events.
 * @param <T> View that this is Presenter can be attached to.
 */
public interface Presenter<T> {

    /**
     * Attached this Presenter to the specified view.
     * @param view
     */
    void attachTo(T view);

    /**
     * Method that respond to the View resume lifecycle state.
     */
    void resume();

    /**
     * Method that respond to the View pause lifecycle state.
     */
    void pause();

    /**
     * Method that respond to the View destroy lifecycle state.
     */
    void destroy();
}
