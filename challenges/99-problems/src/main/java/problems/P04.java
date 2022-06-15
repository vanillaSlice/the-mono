package problems;

import java.util.List;

import static java.util.Objects.requireNonNull;

class P04 {

  static <T> int length(final List<T> list) {
    requireNonNull(list, "list cannot be null");

    return list.size();
  }

}
