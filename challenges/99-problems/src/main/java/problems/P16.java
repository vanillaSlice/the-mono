package problems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

class P16 {

  static <T> List<T> dropEveryNth(final List<T> list, final int n) {
    requireNonNull(list, "list cannot be null");

    final List<T> result = new ArrayList<>(list);

    if (n <= 0 || n >= list.size()) {
      return result;
    }

    int count = 0;
    final Iterator<T> iterator = result.iterator();
    while (iterator.hasNext()) {
      iterator.next();
      count++;
      if (count % n == 0) {
        iterator.remove();
      }
    }

    return result;
  }

}
