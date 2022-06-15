package problems;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

class P07 {

  static <T> List<T> flatten(final List<?> list, final Class<T> elementType) {
    requireNonNull(list, "list cannot be null");
    requireNonNull(elementType, "elementType cannot be null");

    final List<T> flattened = new ArrayList<>();

    list.forEach(e -> {
      if (e instanceof List) {
        flattened.addAll(flatten((List<?>) e, elementType));
      } else {
        flattened.add((T) e);
      }
    });

    return flattened;
  }

}
