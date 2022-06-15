package problems;

import java.util.List;

import static java.util.Objects.requireNonNull;

class P18 {

  static <T> List<T> slice(final List<T> list, final int from, final int to) {
    requireNonNull(list, "list cannot be null");

    return list.subList(from - 1, to);
  }

}
