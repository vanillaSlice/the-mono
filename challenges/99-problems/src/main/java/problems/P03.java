package problems;

import java.util.List;

import static java.util.Objects.requireNonNull;

class P03 {

  static <T> T kth(final List<T> list, final int k) {
    requireNonNull(list, "list cannot be null");

    return list.get(k);
  }

}
