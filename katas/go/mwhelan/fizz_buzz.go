package mwhelan

import (
	"errors"
	"fmt"
)

func FizzBuzz(n int) (string, error) {
	if n <= 0 {
		return "", errors.New("input cannot be less than 1")
	}

	var result string

	divisibleBy3 := n%3 == 0
	divisibleBy5 := n%5 == 0

	if divisibleBy3 {
		result += "Fizz"
	}
	if divisibleBy5 {
		result += "Buzz"
	}
	if len(result) == 0 {
		result = fmt.Sprint(n)
	}

	return result, nil
}
