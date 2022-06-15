package problems;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

class P20 {

  static Object[] removeAt(final List<Object> list, final int n) {
    requireNonNull(list, "list cannot be null");

    final List<Object> copy = new ArrayList<>(list);
    final Object removed = copy.remove(n - 1);

    return new Object[]{copy, removed};
  }

}
