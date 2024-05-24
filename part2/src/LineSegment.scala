/** A line segment connecting two points */
// (You don't need to do anything in this file)
case class LineSegment(start: Point, end: Point):

  /** Returns whether this line segment is vertical (some calculations don't work with vertical lines since they
      have infinite slope) */
  def isVertical: Boolean =
    start.x == end.x

  /** Calculates the slope of the line segment */
  def slope: Double =
    (end.y - start.y) / (end.x - start.x)

  /** Calculates a vector normal to the line segment */
  def normal: Vector =
    val Vector(x, y) = end - start
    Vector(-y, x)

  /** Calculates the point of intersection between two line segments, or None if they do not intersect */
  def intersection(other: LineSegment): Option[Point] =
    import Math.{min, max}
    (this, other) match
      // If both lines are vertical, no intersection
      case (s1, s2) if s1.isVertical && s2.isVertical => None

      // If one of the lines is vertical, special handling (slope not defined)
      case (s1, s2) if s1.isVertical =>
        val m2 = s2.slope
        val LineSegment(Point(x, y1), Point(_, y2)) = s1
        // do s2's x values cross the vertical line?
        if (Math.min(s2.start.x, s2.end.x) < x && x < Math.max(s2.start.x, s2.end.x))
          // they do - is the point at which they cross within the vertical line's y range?
          val y = s2.start.y + m2 * (x - s2.start.x)
          if (Math.min(y1, y2) < y && y < Math.max(y1, y2))
            Some(Point(x, y))
          else
            None
        else
          None

      // If the second line is vertical, put it first so the code above handles it
      case (s1, s2) if s2.isVertical => s2.intersection(s1)

      // Neither line is vertical
      case (s1, s2) =>
        val m1 = s1.slope
        val m2 = s2.slope
        // First line equation: y - s1.end.y = m * (x - s1.end.x) => y = s1.end.y + m1 * (x - s1.end.x)
        // Second line equation:                                     y = s2.end.y + m2 * (x - s2.end.x)
        // Intersection: s1.end.y + m1 * (x - s1.end.x) = s2.end.y + m2 * (x - s2.end.x)
        val ix = ((s1.end.y - m1 * s1.end.x) - (s2.end.y - m2 * s2.end.x)) / (m2 - m1)
        if (min(s1.start.x, s1.end.x) <= ix && ix <= max(s1.start.x, s1.end.x)
            && min(s2.start.x, s2.end.x) <= ix && ix <= max(s2.start.x, s2.end.x))
          Some(Point(ix, s1.start.y + m1 * (ix - s1.start.x)))
        else
          None

  /**
   * Calculates the point of intersection between this line segment and a circle, or None if they do not intersect.
   * Note that there could be two points of intersection; if there are, this method returns the one closer to the
   * line segment's starting point.
   */
  def intersection(circle: Circle): Option[Point] =
    val (x1, y1, x2, y2) = (start.x, start.y, end.x, end.y)
    val (xc, yc, r) = (circle.center.x, circle.center.y, circle.radius)

    val a = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)
    val b = -2 * ((x1 - xc) * (x1 - x2) + (y1 - yc) * (y1 - y2))
    val c = (x1 - xc) * (x1 - xc) + (y1 - yc) * (y1 - yc) - r * r

    // The quadratic formula will always be there for me ♥
    val D = b * b - 4 * a * c
    if (D < 0)
      None
    else
      val t1 = (-b - Math.sqrt(D)) / (2 * a)
      val t2 = (-b + Math.sqrt(D)) / (2 * a)
      if (0 < t1 && t1 < 1)
        Some(Point(x1 + t1 * (x2 - x1), y1 + t1 * (y2 - y1)))
      else if (0 < t2 && t2 < 1)
        Some(Point(x1 + t2 * (x2 - x1), y1 + t2 * (y2 - y1)))
      else
        None
