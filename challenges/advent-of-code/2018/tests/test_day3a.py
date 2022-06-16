from src.day3a import overlap

def test_overlap():
    assert overlap(['#1 @ 1,3: 4x4', '#2 @ 3,1: 4x4', '#3 @ 5,5: 2x2']) == 4
