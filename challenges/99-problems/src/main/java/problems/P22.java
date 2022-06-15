package problems;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class P22 {

  static List<Integer> range(final int from, final int to) {
    return IntStream.range(from, to + 1).boxed().collect(toList());
  }

}
