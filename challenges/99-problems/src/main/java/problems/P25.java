package problems;

import java.util.List;

import static problems.P23.randomSelect;

class P25 {

  static <T> List<T> randomPermutation(final List<T> list) {
    return randomSelect(list, list.size());
  }

}
