package mwhelan

import (
	"testing"
)

func TestFizzBuzz(t *testing.T) {
	cases := []struct {
		in   int
		want string
	}{
		{1, "1"},
		{2, "2"},
		{3, "Fizz"},
		{4, "4"},
		{5, "Buzz"},
		{6, "Fizz"},
		{7, "7"},
		{8, "8"},
		{9, "Fizz"},
		{10, "Buzz"},
		{11, "11"},
		{12, "Fizz"},
		{13, "13"},
		{14, "14"},
		{15, "FizzBuzz"},
		{30, "FizzBuzz"},
	}
	for _, c := range cases {
		got, error := FizzBuzz(c.in)
		if got != c.want || error != nil {
			t.Errorf("FizzBuzz(%v) == %q, %v, want %q, <nil>", c.in, got, error, c.want)
		}
	}
}

func TestFizzBuzzError(t *testing.T) {
	want := "input cannot be less than 1"
	cases := []struct {
		in int
	}{
		{-1},
		{0},
	}
	for _, c := range cases {
		got, error := FizzBuzz(c.in)
		if got != "" || error.Error() != want {
			t.Errorf("FizzBuzz(%v) == %q, %v, want \"\", %q", c.in, got, error, want)
		}
	}
}
