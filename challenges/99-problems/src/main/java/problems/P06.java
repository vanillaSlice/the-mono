package problems;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static problems.P05.reverse;

class P06 {

  static <T> boolean isPalindrome(final List<T> list) {
    requireNonNull(list, "list cannot be null");

    return list.equals(reverse(list));
  }

}
