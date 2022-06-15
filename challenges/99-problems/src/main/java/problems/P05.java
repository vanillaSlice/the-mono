package problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

class P05 {

  static <T> List<T> reverse(final List<T> list) {
    requireNonNull(list, "list cannot be null");

    final List<T> copy = new ArrayList<>(list);
    Collections.reverse(copy);
    return copy;
  }

}
