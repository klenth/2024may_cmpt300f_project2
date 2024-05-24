import org.junit.Test
import org.junit.Assert.*

/** Tests of the Point and Vector operators */
// Do not change any code in this file!
class TestPointVector:

  @Test
  def pointPlusVector(): Unit =
    assertEquals("(5, 10) + [-3, 4] = (2, 14)", Point(2, 14), Point(5, 10) + Vector(-3, 4))
    assertEquals("(-15, 6) + [15, 6] = (0, 12)", Point(0, 12), Point(-15, 6) + Vector(15, 6))

  @Test
  def pointMinusPoint(): Unit =
    assertEquals("(5, 10) - (-15, 6) = [20, 4]", Vector(20, 4), Point(5, 10) - Point(-15, 6))
    assertEquals("(-6, 2) - (14, 40) = [-20, -38]", Vector(-20, -38), Point(-6, 2) - Point(14, 40))

  @Test
  def pointToString(): Unit =
    assertEquals("(3.5, -4.5)", Point(3.5, -4.5).toString)

  @Test
  def vectorTimesDouble(): Unit =
    assertEquals("[15, -3] * 4 = [60, -12]", Vector(60, -12), Vector(15, -3) * 4.0)
    assertEquals("[0, 3.2] * 8 = [0, 25.6]", Vector(0, 25.6), Vector(0, 3.2) * 8.0)

  @Test
  def vectorPlusVector(): Unit =
    assertEquals("[-14, 16] + [5.5, 8.25] = [-8.5, 24.25]", Vector(-8.5, 24.25), Vector(-14, 16) + Vector(5.5, 8.25))
    assertEquals("[6, 7] + [4, 5] = [10, 12]", Vector(10, 12), Vector(6, 7) + Vector(4, 5))

  @Test
  def vectorMinusVector(): Unit =
    assertEquals("[6, 7] - [4, 5] = [2, 2]", Vector(2, 2), Vector(6, 7) - Vector(4, 5))
    assertEquals("[-14, 16] - [5.5, 8.25] = [-19.5, 7.75]", Vector(-19.5, 7.75), Vector(-14, 16) - Vector(5.5, 8.25))

  @Test
  def vectorPlusPoint(): Unit =
    assertEquals("[4, 5] + (3, 9) = (7, 14)", Point(7, 14), Vector(4, 5) + Point(3, 9))
    assertEquals("[-15, -100] + (40, 200) = (25, 100)", Point(25, 100), Vector(-15, -100) + Point(40, 200))

  @Test
  def minusVector(): Unit =
    assertEquals("-[4, 0] = [-4, 0]", Vector(-4, 0), -Vector(4, 0))
    assertEquals("-[6, -8] = [-6, 8]", Vector(-6, 8), -Vector(6, -8))

  @Test
  def vectorDotVector(): Unit =
    assertEquals("[3, 4] dot [5, 6] = 39", 39, Vector(3, 4) dot Vector(5, 6), 0)
    assertEquals("[-5, 4] dot [4, 5] = 0", 0, Vector(-5, 4) dot Vector(4, 5), 0)

  @Test
  def vectorToString(): Unit =
    assertEquals("[3.5, -4.5]", Vector(3.5, -4.5).toString)