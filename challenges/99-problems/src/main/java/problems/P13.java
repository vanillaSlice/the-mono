package problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.Objects.requireNonNull;

class P13 {

  static <T> List<SimpleEntry<Integer, T>> encode(final List<T> list) {
    requireNonNull(list, "list cannot be null");

    final List<SimpleEntry<Integer, T>> encoded = new ArrayList<>();

    boolean firstIteration = true;
    T previousElement = null;
    for (final T e : list) {
      if (!firstIteration && Objects.equals(e, previousElement)) {
        final SimpleEntry<Integer, T> previousEntry = encoded.remove(encoded.size() - 1);
        encoded.add(new SimpleEntry<>(previousEntry.getKey() + 1, previousEntry.getValue()));
      } else {
        encoded.add(new SimpleEntry<>(1, e));
      }
      previousElement = e;
      firstIteration = false;
    }

    return encoded;
  }

}
