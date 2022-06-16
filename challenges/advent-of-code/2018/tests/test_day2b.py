from src.day2b import common_chars

def test_common_chars_first_char():
    assert common_chars(['abcde', 'fghij', 'klmno', 'pqrst', 'bbcde', 'wvxyz']) == 'bcde'

def test_common_chars_middle_char():
    assert common_chars(['abcde', 'fghij', 'klmno', 'pqrst', 'fguij', 'axcye', 'wvxyz']) == 'fgij'

def test_common_chars_last_char():
    assert common_chars(['abcde', 'fghij', 'klmno', 'pqrst', 'wvxyz', 'pqrsp']) == 'pqrs'
