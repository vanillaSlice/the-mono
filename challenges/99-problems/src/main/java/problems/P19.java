package problems;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

class P19 {

  static <T> List<T> rotate(final List<T> list, final int n) {
    requireNonNull(list, "list cannot be null");

    final List<T> result = new ArrayList<>();

    final int split = n > 0 ? n : n + list.size();

    final List<T> left = list.subList(split, list.size());
    final List<T> right = list.subList(0, split);

    result.addAll(left);
    result.addAll(right);

    return result;
  }

}
