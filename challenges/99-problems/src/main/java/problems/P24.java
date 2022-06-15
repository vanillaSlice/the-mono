package problems;

import java.util.List;

class P24 {

  static List<Integer> randomSelect(final int n, final int min, final int max) {
    return P23.randomSelect(P22.range(min, max), n);
  }

}
