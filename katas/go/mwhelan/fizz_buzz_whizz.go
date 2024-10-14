package mwhelan

import (
	"errors"
	"fmt"
)

func FizzBuzzWhizz(n int) (string, error) {
	if n < 1 {
		return "", errors.New("input cannot be less than 1")
	}

	var result string

	divisibleBy3 := n%3 == 0
	divisibleBy5 := n%5 == 0
	isPrime := isPrime(n)

	if divisibleBy3 {
		result += "Fizz"
	}
	if divisibleBy5 {
		result += "Buzz"
	}
	if isPrime {
		result += "Whizz"
	}
	if len(result) == 0 {
		result = fmt.Sprint(n)
	}

	return result, nil
}

func isPrime(n int) bool {
	if n == 1 {
		return false
	}
	for i := 2; i <= n/2; i++ {
		if n%i == 0 {
			return false
		}
	}
	return true
}
