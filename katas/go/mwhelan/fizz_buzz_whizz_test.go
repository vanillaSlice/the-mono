package mwhelan

import "testing"

func TestFizzBuzzWhizz(t *testing.T) {
	cases := []struct {
		in   int
		want string
	}{
		{1, "1"},
		{2, "Whizz"},
		{3, "FizzWhizz"},
		{4, "4"},
		{5, "BuzzWhizz"},
		{6, "Fizz"},
		{7, "Whizz"},
		{8, "8"},
		{9, "Fizz"},
		{10, "Buzz"},
		{11, "Whizz"},
		{12, "Fizz"},
		{13, "Whizz"},
		{14, "14"},
		{15, "FizzBuzz"},
		{30, "FizzBuzz"},
	}
	for _, c := range cases {
		got, error := FizzBuzzWhizz(c.in)
		if got != c.want || error != nil {
			t.Errorf("FizzBuzzWhizz(%v) == %q, %q, want %q, <nil>", c.in, got, error, c.want)
		}
	}
}

func TestFizzBuzzWhizzError(t *testing.T) {
	want := "input cannot be less than 1"
	cases := []struct {
		in int
	}{
		{-1},
		{0},
	}
	for _, c := range cases {
		got, error := FizzBuzzWhizz(c.in)
		if got != "" || error.Error() != want {
			t.Errorf("FizzBuzzWhizz(%v) == %q, %v, want \"\", %q", c.in, got, error, want)
		}
	}
}
