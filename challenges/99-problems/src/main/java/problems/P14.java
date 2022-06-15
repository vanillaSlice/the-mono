package problems;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

class P14 {

  static <T> List<T> duplicate(final List<T> list) {
    requireNonNull(list, "list cannot be null");

    return list.stream().flatMap(e -> of(e, e)).collect(toList());
  }

}
