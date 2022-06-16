from src.day2a import box_checksum

def test_box_checksum_no_twos_and_no_threes():
    assert box_checksum(['abababab', 'c', 'ddddd', 'abababababababab', 'z']) == 0

def test_box_checksum_no_twos_and_multiple_threes():
    assert box_checksum(['abaa', 'bbb', 'ccc', 'a', 'bc']) == 0

def test_box_checksum_multiple_twos_and_no_threes():
    assert box_checksum(['aab', 'bb', 'c', 'a', 'bc']) == 0

def test_box_checksum_multiple_twos_and_multiple_threes():
    assert box_checksum(['abcdef', 'bababc', 'abbcde', 'abcccd', 'aabcdd', 'abcdee', 'ababab']) == 12
