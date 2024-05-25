/** A (2D) vector, consisting of an x (horizontal component) and y (vertical component) */
case class Vector(x: Double, y: Double):

  // Add the following to this class:
  
  // Operator overload: Vector * Double => Vector
  // [x, y] * a = [x * a, y * z]
  
  
  // Operator overload: Vector + Vector => Vector
  // [x, y] + [z, w] = [x + z, y + w]
  
  
  // Operator overload: Vector - Vector => Vector
  // [x, y] - [z, w] = [x - z, y - w]
  
  
  // Operator overload: Vector + Point => Point
  // [x, y] + (p, q) = (x + p, y + q)
  
  
  // Operator overload: -Vector => Vector
  // -[x, y] = [-x, -y]
  // (Remember that you'll need to call this one `unary_-`)
  
  
  // Operator: Vector dot Vector => Double
  // [x, y] dot [z, w] = x*z + y*w
  // (You can define this just the same as the operator overloads,
  // but with infix: "infix def dot...")


  // Override toString to return the form "[x, y]"
  
  def length: Double =
    Math.sqrt(x * x + y * y)
  
  /** Projects this vector onto other */
  def projection(other: Vector) =
    other * ((this dot other) / (other dot other))

  /** Reflects this vector about other */
  def reflect(other: Vector) =
    this - this.projection(other) * 2
  
  /** Rotates this vector by the given angle (in radians) */
  def rotate(angle: Double): Vector =
    Vector(x * Math.cos(angle) + y * Math.sin(angle),
           y * Math.cos(angle) - x * Math.sin(angle))
