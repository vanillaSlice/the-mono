package problems;

import java.util.ArrayList;
import java.util.List;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.Collections.nCopies;
import static java.util.Objects.requireNonNull;

class P12 {

  static <T> List<T> decode(final List<Object> list) {
    requireNonNull(list, "list cannot be null");

    final List<T> decoded = new ArrayList<>();

    list.forEach(e -> {
      if (e instanceof SimpleEntry) {
        final SimpleEntry<Integer, T> entry = (SimpleEntry<Integer, T>) e;
        decoded.addAll(nCopies(entry.getKey(), entry.getValue()));
      } else {
        decoded.add((T) e);
      }
    });

    return decoded;
  }

}
