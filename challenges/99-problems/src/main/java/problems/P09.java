package problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

class P09 {

  static <T> List<List<T>> pack(final List<T> list) {
    requireNonNull(list, "list cannot be null");

    final List<List<T>> packed = new ArrayList<>();

    T previousElement = null;
    List<T> currentList = new ArrayList<>();
    for (final T e : list) {
      if (!Objects.equals(previousElement, e)) {
        currentList = new ArrayList<>();
        packed.add(currentList);
      }
      currentList.add(e);
      previousElement = e;
    }

    return packed;
  }

}
