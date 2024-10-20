package iteration

import "strings"

// Repeat takes a character and repeats it the provided number of times.
func Repeat(character string, times int) string {
	var repeated strings.Builder
	for i := 0; i < times; i++ {
		repeated.WriteString(character)
	}
	return repeated.String()
}
