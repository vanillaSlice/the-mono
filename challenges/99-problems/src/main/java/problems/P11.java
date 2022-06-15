package problems;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

class P11 {

  static <T> List<Object> encode(final List<T> list) {
    requireNonNull(list, "list cannot be null");

    return P10.encode(list).stream()
        .map(e -> e.getKey() == 1 ? e.getValue() : e)
        .collect(toList());
  }

}
