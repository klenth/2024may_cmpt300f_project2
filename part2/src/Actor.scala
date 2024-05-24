/** An "actor" - something interactive that can be displayed in a field */
// (You don't need to do anything in this file)
enum Actor:

  /** A dot that bounces around in a field. */
  case Ball(location: Point, velocity: Vector)

  /** A barrier that balls bounce off of. */
  case Wall(lineSegment: LineSegment)

  /** A circle that balls bounces off of. */
  case Bumper(circle: Circle)

  private def close(x: Double, y: Double): Boolean =
    Math.abs(x - y) < 1e-8

  override def equals(obj: Any): Boolean = (this, obj) match
    case (b1: Ball, b2: Ball) =>
      close(b1.location.x, b2.location.x)
      && close(b1.location.y, b2.location.y)
      && close(b1.velocity.x, b2.velocity.x)
      && close(b1.velocity.y, b2.velocity.y)
    case _ => super.equals(obj)
