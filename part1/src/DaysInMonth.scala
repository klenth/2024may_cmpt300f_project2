// Part 1: practice using Option objects
//
// Scala's "Option" represents an optional value. It's defined like this:
//   enum Option[T]:
//     case Some(value: T)
//     case None
// In other words, an Option[T] either holds a value of type T (Some) or it
// is empty (None). It's essentially a collection that holds 0 or 1 values.
// (Remember that Scala uses [ ] for type parameters: Option[T] would be
// written Option<T> in Java.)
//
// You can use pattern matching with an Option to check whether it has a value:
//   option match {
//     case Some(value) => ... // do something with value
//     case None =>        ... // no value
//   }
//
// In Scala, we pretty much never use "null" (unless interfacing with Java code
// that requires it). It is intended that we use Option in almost all cases where
// a value could be missing instead. (Incidentally, Java has its own version of
// Option: java.util.Optional.)
//
// For this part,
//   1) Fill in the monthName() method, that should return the name of the month
//     ("January" for 1, "February" for 2, etc.) in an Option if the month number
//     is 1 to 12, or an empty Option if it's not. (So return either Some(...) or
//     None.)
//
//   2) Similarly, fill in the days() method, that returns the number of days in
//     the given month and year. (The year is necessary because February changes
//     depending on whether it's a leap year!) You can use the provided
//     isLeapYear() method to check whether the year is a leap year.
//
//       1  (January)  - 31 days
//       2  (February) - 29 days in a leap year, 28 otherwise
//       3  (March)    - 31 days
//       4  (April)    - 30 days
//       5  (May)      - 31 days
//       6  (June)     - 30 days
//       7  (July)     - 31 days
//       8  (August)   - 31 days
//       9  (September)- 30 days
//       10 (October)  - 31 days
//       11 (November) - 30 days
//       12 (December) - 31 days
//
//   3) Finally, add code in main() following the comments (print out the month
//     name and number of days).
//
//   The TestDaysInMonth tester (which should already be in your run
//   configurations) will run some tests on your monthName() and days() methods.

import java.util.*;

object DaysInMonth:

  def isLeapYear(year: Int): Boolean =
    (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)

  def monthName(month: Int): Option[String] =
    None  // Replace with your code

  def days(month: Int, year: Int): Option[Int] =
    None  // Replace with your code

  def main(args: Array[String]): Unit =
    val in = new Scanner(System.in)

    while (true)
      print("Month: ")
      val month = in.nextInt()

      // Write code that calls monthName() and prints out the
      // resulting name of the month. If it returns None, then
      // print out "That's not a valid month!"

      print("Year: ")
      val year = in.nextInt()

      // Write code that calls days() and prints out how many
      // days the month has.

