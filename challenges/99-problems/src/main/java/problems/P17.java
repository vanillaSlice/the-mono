package problems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

class P17 {

  static <T> Map<Boolean, List<T>> split(final List<T> list, final int index) {
    requireNonNull(list, "list cannot be null");

    final Map<Boolean, List<T>> result = new HashMap<>();

    result.put(true, list.subList(0, index));
    result.put(false, list.subList(index, list.size()));

    return result;
  }

}
