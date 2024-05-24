import org.junit.Test
import org.junit.Assert.*

/** Tests of the DaysInMonth methods */
// Do not change any code in this file!
class TestDaysInMonth:

  @Test
  def isLeapYear(): Unit =
    import DaysInMonth as dim
    assertFalse("2025 is not a leap year", dim.isLeapYear(2025))
    assertTrue("2024 is a leap year", dim.isLeapYear(2024))
    assertFalse("2100 will not be a leap year", dim.isLeapYear(2100))
    assertTrue("2000 was a leap year", dim.isLeapYear(2000))
    assertFalse("1987 was not a leap year", dim.isLeapYear(1987))
    assertTrue("1980 was a leap year", dim.isLeapYear(1980))
    assertFalse("1900 was not a leap year", dim.isLeapYear(1900))

  @Test
  def daysInRegularMonths(): Unit =
    import DaysInMonth as dim
    assertEquals("March has 31 days in a leap year", Some(31), dim.days(3, 2024))
    assertEquals("March has 31 days in a normal year", Some(31), dim.days(3, 2025))
    assertEquals("November has 30 days in a leap year", Some(30), dim.days(11, 1980))
    assertEquals("November has 30 days in a normal year", Some(30), dim.days(11, 1981))

  @Test
  def daysInInvalidMonth(): Unit =
    import DaysInMonth as dim
    assertEquals("days(0, leap year) returns None", None, dim.days(0, 1994))
    assertEquals("days(0, normal year) returns None", None, dim.days(0, 1997))
    assertEquals("days(50, leap year) returns None", None, dim.days(50, 2016))

  @Test
  def daysInFebruary(): Unit =
    import DaysInMonth as dim
    assertEquals("February has 29 days in 2024", Some(29), dim.days(2, 2024))
    assertEquals("February has 28 days in 2025", Some(28), dim.days(2, 2025))
    assertEquals("February had 29 days in 2000", Some(29), dim.days(2, 2000))
    assertEquals("February will have 28 days in 2100", Some(28), dim.days(2, 2100))

  @Test
  def monthName(): Unit =
    import DaysInMonth as dim
    assertEquals("Month 4 is April", Some("April"), dim.monthName(4))
    assertEquals("Month 10 is October", Some("October"), dim.monthName(10))
    assertEquals("monthName(0) returns None", None, dim.monthName(0))
    assertEquals("monthName(15) returns None", None, dim.monthName(15))