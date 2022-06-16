from src.day1a import chronal_calibration

def test_chronal_calibration_all_positive():
    assert chronal_calibration(['+1', '+1', '+1']) == 3

def test_chronal_calibration_all_negative():
    assert chronal_calibration(['-1', '-2', '-3']) == -6

def test_chronal_calibration_mixed():
    assert chronal_calibration(['+1', '+1', '-2']) == 0

def test_chronal_calibration_larger_numbers():
    assert chronal_calibration(['+1000', '+101', '-200']) == 901
