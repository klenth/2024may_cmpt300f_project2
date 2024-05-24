/**
 * An impact made by some particle (like a ball) on something else.
 *   - impactedActor: what it is that the particle hit
 *   - point: the point of impact
 *   - normal: normal vector of the surface of the impacted actor at the point of impact (necessary for calculating
 *     the direction that the particle will bounce in)
 *   - time: the time at which the impact occurs
 */
// (You don't need to do anything in this file)
case class Impact(impactedActor: Actor, point: Point, normal: Vector, time: Double)
