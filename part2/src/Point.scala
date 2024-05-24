/** A (2D) point, consisting of x- and y-coordinates. */
case class Point(x: Double, y: Double):

  // Add the following to this class:
  
  // Operator overload: Point + Vector => Point
  // (x, y) + [a, b] = (x + a, y + b)
  
  
  // Operator overload: Point - Point => Vector
  // (x, y) - (z, w) = [x - z, y - w]
  
  
  // Override toString to return the form "(x, y)"
  
  def +(v: Vector): Point =
    Point(x + v.x, y + v. y)

  def -(p: Point): Vector =
    Vector(x - p.x, y - p.y)

  override def toString: String =
    s"($x, $y)"
    