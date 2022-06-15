package problems;

import java.util.List;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static problems.P09.pack;

class P10 {

  static <T> List<SimpleEntry<Integer, T>> encode(final List<T> list) {
    requireNonNull(list, "list cannot be null");

    return pack(list).stream()
        .map(e -> new SimpleEntry<>(e.size(), e.get(0)))
        .collect(toList());
  }

}
