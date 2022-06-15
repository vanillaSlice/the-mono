package problems;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

class P21 {

  static <T> List<T> insertAt(final List<T> list, final int index, final T item) {
    requireNonNull(list, "list cannot be null");

    final List<T> result = new ArrayList<>(list);

    result.add(index - 1, item);

    return result;
  }

}
