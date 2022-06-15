package problems;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

class P01 {

  static <T> T last(final List<T> list) {
    requireNonNull(list, "list cannot be null");

    if (list.isEmpty()) {
      throw new NoSuchElementException("list is empty");
    }

    return list.get(list.size() - 1);
  }

}
