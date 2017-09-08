package br.com.ecarrara.popularmovies.core.presentation;

/**
 * A Presenter specification that is able to respond to common Android View events.
 * @param <T> View that this is Presenter can be attached to.
 * @param <P> Data that this is Presenter can use to initialize the View.
 */
public interface Presenter<T, P> {

    /**
     * Attached this Presenter to the specified view.
     * @param view to attach the presenter
     */
    void attachTo(T view);

    /**
     * Attached this Presenter to the specified view making usage of the provided data.
     * @param view to attach the presenter
     * @param data to be used to initialize the view
     */
    void attachTo(T view, P data);

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
