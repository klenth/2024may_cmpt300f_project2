import java.awt.{BasicStroke, Color, Dimension, Graphics, Graphics2D, RenderingHints}
import java.util.Random
import javax.swing.*
import scala.compiletime.uninitialized
import Actor.*

import java.awt.image.BufferedImage

// Delete this
import javax.imageio.ImageIO
import java.io.File

import scala.annotation.tailrec

/** The field that the balls and obstacles are displayed on. All the animation code is here. */
class Field(width: Int, height: Int) extends JComponent:

  // How long to wait between rendering frames (ms)
  // (20 ms gives about 50 fps)
  // smaller value means smoother animation but also more CPU used!
  private val frameSleep: Long = 50

  private val random = Random()
  private val stopwatch = Stopwatch()

  // Adjust this to change what actors will be displayed
  // (A Seq is just an immutable Array - more common to use these than Arrays in Scala)
  private var actors: Seq[Actor] =
    (1 to 3).map(_ => randomBumper)       // change the number after "to" to adjust how many bumpers are added
      ++ (1 to 50).map(_ => randomBall)    // likewise
      ++ (1 to 3).map(_ => randomWall)    // likewise
      ++ randomQuadrilateral
      ++ boundaryWalls

  private var animator: Thread = uninitialized
  private var running: Boolean = false

  // Delete this
  private var frame: Int = 0

  // Picks a random point within the field (used by other "random" methods)
  def randomPoint: Point =
    Point(random.nextDouble(width), random.nextDouble(height))

  // Generate a random ball somewhere in the field with random speed and direction
  def randomBall: Actor =
    val speed = 100 + random.nextDouble(200)
    Ball(randomPoint, Vector(speed, 0).rotate(Math.random() * 2 * Math.PI))

  // Generates a randomly placed wall
  def randomWall: Actor =
    Wall(LineSegment(randomPoint, randomPoint))

  // Generates a random quadrilateral, with one vertex each on the four sides of the field
  def randomQuadrilateral: Seq[Actor] =
    val top = Point(random.nextDouble() * width, 0)
    val right = Point(width - 1, random.nextDouble() * height)
    val bottom = Point(random.nextDouble() * width, height - 1)
    val left = Point(0, random.nextDouble() * height)
    Seq(
      Wall(LineSegment(top, right)),
      Wall(LineSegment(bottom, right)),
      Wall(LineSegment(bottom, left)),
      Wall(LineSegment(left, top))
    )

  // Returns walls on the four sides of the field (without these, the balls have a tendency to escape!)
  def boundaryWalls: Seq[Actor] =
    val tl = Point(0, 0)
    val tr = Point(width - 1, 0)
    val bl = Point(0, height - 1)
    val br = Point(width - 1, height - 1)
    Seq(
      Wall(LineSegment(tl, tr)),
      Wall(LineSegment(tr, br)),
      Wall(LineSegment(br, bl)),
      Wall(LineSegment(bl, tl))
    )

  // Generates a bumper of random location and size
  def randomBumper: Bumper =
    Bumper(Circle(
      randomPoint,
      random.nextDouble() * width / 3 + 20
    ))

  // Starts the animation
  def start(): Unit = synchronized:
    if (!running)
      running = true
      stopwatch.reset()
      animator = Thread(() =>
        while (running)
          Thread.sleep(frameSleep)
          update()
      )
      animator.start()

  // Stops the animation
  def stop(): Unit = synchronized:
    running = false

  // Returns whether the animation is currently running
  def isRunning: Boolean = synchronized:
    running

  def setActors(actors: Seq[Actor]): Unit =
    this.actors = actors

  override def getPreferredSize: Dimension =
    Dimension(width, height)

  // Draw the field
  override def paintComponent(_g: Graphics): Unit =
    // Uncomment the line below if the graphics are laggy on your system
    // java.awt.Toolkit.getDefaultToolkit.sync()

    val g = _g.asInstanceOf[Graphics2D]
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g.setStroke(new BasicStroke(3))
    // Draw all the actors
    actors.foreach(paintActor(g, _))

    // Delete this
    val image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val g2 = image.createGraphics()
    g2.setPaint(Color.WHITE)
    g2.fillRect(0, 0, width, height)
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2.setStroke(new BasicStroke(3))
    g2.setPaint(Color.BLACK)
    actors.foreach(paintActor(g2, _))
    try {
      ImageIO.write(image, "png", File(f"frame_$frame%04d.png"))
    } catch
      case ex => ex.printStackTrace()
    frame += 1

  // Rules for drawing each kind of actor
  private def paintActor(g: Graphics2D, actor: Actor): Unit = actor match
    case Ball(Point(x, y), _) =>
      g.fillOval((x - 5).toInt, (y - 5).toInt, 10, 10)
    case Wall(LineSegment(Point(x1, y1), Point(x2, y2)))  =>
      g.drawLine(x1.toInt, y1.toInt, x2.toInt, y2.toInt)
    case Bumper(Circle(Point(x, y), r)) =>
      g.drawOval((x - r).toInt, (y - r).toInt, (2 * r).toInt, (2 * r).toInt)
    case _ =>
      throw new RuntimeException(s"No paintActor() rule for $actor")

  // Update actors & repaint the screen
  private def update(): Unit =
    // Replace actors with the result of updating actors to the next frame
    actors = actors.map(updateActor(_, stopwatch.timeElapsed))
    stopwatch.reset()
    repaint()

  /** Returns all impacts that a ball would make during the next slice of time while moving at its current
      velocity (an empty Seq if there will be none) */
  private def findBallImpacts(ball: Ball, time: Double): Seq[Impact] =
    Seq() // replace this with your own code!
    val ballSegment = LineSegment(ball.location, ball.location + ball.velocity * time)
    val timeToReach: Point => Double = p => (p.x - ball.location.x) / ball.velocity.x
    actors.flatMap(actor => actor match {
      case wall: Wall =>
        ballSegment.intersection(wall.lineSegment) match
          case Some(p) => Some(Impact(wall, p, wall.lineSegment.normal, timeToReach(p)))
          case _ => None

      case bumper: Bumper =>
        ballSegment.intersection(bumper.circle) match
          case Some(p) => Some(Impact(bumper, p, p - bumper.circle.center, timeToReach(p)))
          case _ => None

      case _ => None
    })

  /** Returns the first impact that the ball will make during the next slice of time, or None if there will be none */
  private def findNextBallImpact(ball: Ball, time: Double, lastBounced: Option[Actor] = None): Option[Impact] =
    None // Replace this with your own code
    findBallImpacts(ball, time)
      .filterNot(impact => lastBounced.contains(impact.impactedActor))
      .sorted(Ordering.by(_.time))
      .headOption

  /**
   * Updates an actor for the next time.
   */
  /*
   *   - If the actor is a ball, then check whether there is an impact during the next slice of time (by using
   *     findNextBallImpact()).
   *     - If there is no impact, then update the ball to keep moving in the same direction:
   *       new location: old location + velocity * time
   *       new velocity: same
   *     - If there is an impact, then update the ball to reflect the impact:
   *       new location: point of impact;
   *       new velocity: the result of reflecting the velocity around the impact surface normal vector
   *       ***Then call updateActor() again because the ball could impact something else during the same time slice!***
   *       (Balls tend to escape at corners if you forget to do this!) Pass in the actor that was impacted to the
   *       next updateActor() call so that we don't inadvertantly re-bounce off the same surface immediately.
   *   - If any other kind of actor, just return the same actor again (in the current version, only balls move)
   */
  @tailrec
  final def updateActor(actor: Actor, time: Double, lastBounced: Option[Actor] = None): Actor =
    actor // Replace this with your own code
    actor match
      case ball: Ball => findNextBallImpact(ball, time, lastBounced) match
        case Some(Impact(impactedActor, impactPoint, impactNormal, impactTime)) =>
          val bouncedBall = Ball(impactPoint, ball.velocity.reflect(impactNormal))
          updateActor(bouncedBall, time - impactTime, Some(impactedActor))
        case None =>
          ball.copy(location = ball.location + ball.velocity * time)
      case actor => actor

