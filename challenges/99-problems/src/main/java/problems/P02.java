package problems;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

class P02 {

  static <T> T secondLast(final List<T> list) {
    requireNonNull(list, "list cannot be null");

    if (list.isEmpty()) {
      throw new NoSuchElementException("list is empty");
    } else if (list.size() == 1) {
      throw new NoSuchElementException("list has single element");
    }

    return list.get(list.size() - 2);
  }

}
