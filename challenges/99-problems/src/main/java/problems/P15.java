package problems;

import java.util.List;

import static java.util.Collections.nCopies;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

class P15 {

  static <T> List<T> duplicate(final List<T> list, final int times) {
    requireNonNull(list, "list cannot be null");

    return list.stream().flatMap(e -> nCopies(times, e).stream()).collect(toList());
  }

}
