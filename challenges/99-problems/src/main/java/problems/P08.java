package problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

class P08 {

  static <T> List<T> compress(final List<T> list) {
    requireNonNull(list, "list cannot be null");

    final List<T> compressed = new ArrayList<>();

    T prev = null;
    for (final T e : list) {
      if (!Objects.equals(e, prev)) {
        compressed.add(e);
      }
      prev = e;
    }

    return compressed;
  }

}
