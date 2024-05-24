import org.junit.Test
import org.junit.Assert.*

import Actor.*

/** Tests of the Field class */
// Do not change any code in this file!
class TestField:

  private def withActors(actors: Actor*): Field =
    val field = Field(500, 500)
    field.setActors(actors)
    field

  @Test
  def `0_updateActor_no_collisions`(): Unit =
    val field = withActors()
    val zeroBall = Ball(Point(0, 0), Vector(0, 0))
    val constVelocity = Vector(2, 3)
    assertEquals("Ball with zero velocity stays put", zeroBall,
      withActors(zeroBall).updateActor(zeroBall, 1.0))
    assertEquals("Ball with velocity moves", Ball(Point(0.25, 0.375), constVelocity),
      withActors(Ball(Point(0, 0), constVelocity)).updateActor(Ball(Point(0, 0), constVelocity), 0.125))

  @Test
  def `1_updateActor_simple_collision`(): Unit =
    val ball1 = Ball(Point(498, 250), Vector(100, 0))
    val wall1 = Wall(LineSegment(Point(500, 0), Point(500, 500)))

    assertEquals("Ball moving horizontally hits vertical wall", Ball(Point(489.5, 250), Vector(-100, 0)),
      withActors(ball1, wall1).updateActor(ball1, 0.125))

    val ball2 = Ball(Point(200, 295), Vector(50, 50))
    val wall2 = Wall(LineSegment(Point(100, 300), Point(300, 300)))
    assertEquals("Ball moving diagonally bounces off horizontal wall", Ball(Point(212.5, 292.5), Vector(50, -50)),
      withActors(ball2, wall2).updateActor(ball2, 0.25))

    val wall3 = Wall(LineSegment(Point(100, 250), Point(300, 350)))
    assertEquals("Ball moving diagonally bounces off inclined wall", Ball(Point(231, 308), Vector(70, 10)),
      withActors(ball1, ball2, wall3).updateActor(ball2, 0.5))
  