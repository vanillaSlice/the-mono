package problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Objects.requireNonNull;

class P23 {

  private static final Random RANDOM = new Random();

  static <T> List<T> randomSelect(final List<T> list, final int n) {
    requireNonNull(list, "list cannot be null");

    final List<T> copy = new ArrayList<>(list);
    final List<T> result = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      final int selected = RANDOM.nextInt(copy.size());
      result.add(copy.get(selected));
      copy.remove(selected);
    }

    return result;
  }

}
