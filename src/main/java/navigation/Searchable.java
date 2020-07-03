package navigation;

import java.util.List;

/**
 * Interface for search in deep and width.
 *
 * @param <T> for all Object to search.
 */
public interface Searchable<T> {

    List<T> searchInDeep(String text);

    List<T> searchInWidth(String text);
}
