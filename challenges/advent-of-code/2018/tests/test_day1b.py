from src.day1b import chronal_calibration

def test_chronal_calibration_no_loops():
    assert chronal_calibration(['+1', '-1']) == 0

def test_chronal_calibration_one_loop():
    assert chronal_calibration(['+3', '+3', '+4', '-2', '-4']) == 10

def test_chronal_calibration_multiple_loops():
    assert chronal_calibration(['+7', '+7', '-2', '-7', '-4']) == 14

def test_chronal_calibration_larger_number():
    assert chronal_calibration(['-600', '+300', '+800', '+500', '-600']) == 500
