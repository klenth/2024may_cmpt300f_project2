# Part 2 directions

Part 1 was just a warm-up to give you a little practice working with `Option` objects. In Part 2, you're going to fill
in some pieces in a program that simulates balls bouncing around in a field with a number of obstacles.

<img src="animation.gif">

Most of the code is already written for you; you'll just need to fill in some of the pieces.

# Step 1: fill in the `Point` and `Vector` case classes
The code in `part2` includes two very important case classes, `Point` and `Vector`, that are used extensively to
simulate the motion of the balls within the field. The classes are already defined, but you will need to add some
methods to both to make them fully functional.

**Note:** the code in several other files uses `Point` and `Vector`, so until you implement everything specified below,
there will be lots of compile errors!

Add the following to `Point`, as the comments in the file instruct:
- an overload of the `Point + Vector => Point` operator (adds a `Point` and a `Vector` and returns a `Point`)
- an overload of the `Point - Vector => Point` operator (subtracts a `Vector` from a `Point`)
- an override of `toString` so that points are printed in `(x, y)` form

Add the following to `Vector`, again following the comments:
- overloads of the following operators:
  - `Vector * Double => Vector`
  - `Vector + Vector => Vector`
  - `Vector - Vector => Vector`
  - `Vector + Point => Point`
  - `-Vector => Vector` (the method will need to be named `` `unary_-` ``)
- a new operator `dot`, as in `Vector dot Vector => Double`
  - This is not an overload, since this isn't an operator in the language, but it's written pretty much the same way. Make sure to use `infix` (as in `infix def dot ...`) to tell Scala it's meant to be written as an infix operator!
- an override of `toString` so that vectors are printed in `[x, y]` form

## Testing your code
When you have these written, run the "Part 2: TestPointVector" test suite and it will run some test cases through your
`Point` and `Vector` classes.

# Step 2: handle ball motion without collisions
Now you'll add some code to `Field` to make the balls move. (If you run it as-is, you should see that all the balls just
stay in place). You might want to look at the code in `Field` in general, but for this step you just need to add some
code to the `updateActor()` method, that encodes how each actor on the field (walls, bumpers, and balls) will change as
time passes.

Like all animations, this program operates by time slicing: it draws the current state of everything, then waits a small
length of time (configurable by the `frameSleep` variable), calculates the new states of all actors, and then draws
again, continuing this process until the program ends. (Or until the user hits the space bar, which pauses the
animation.) The `updateActor()` method takes actor and time parameters, and returns the new state of that actor after
time has passed. (It also has a `lastBounced` parameters, but you should ignore that for now.)

Of the three kinds of actors that have been defined — balls, walls, and bumpers — balls are the only ones that move, so
`updateActor()` only needs to do anything if the actor is a ball (otherwise it will just return the same actor back,
signifying that it hasn't changed).

For right now, start by just getting the balls moving, without worrying about whether they have hit any obstacles. The
formula for this is
> `new location = old location + velocity × time`

...which is easily calculated using the operator overloads you defined in the last step.

Fill in `updateActor()` so that, for a ball, it uses the formula above to calculate the new location of the ball (the
ball's velocity should remain unchanged), and for any other kind of actor, it just returns the actor back. Then run the
app and check that the balls move! How exciting.

## Testing your code
Run the "Part 2: TestField" tester. Most of the tests will still fail at this point, but if you've done this correctly,
the `2_updateActor_no_collisions` test should pass.

# Step 3: collisions
Adding collision detection is harder than you might think, but all the heavy lifting in math has already been coded for
you (mostly in the `LineSegment` class, that provides methods for checking whether two line segments intersect and
whether a line segment intersects with a circle). You will need to glue the pieces together by writing Scala code,
however!

## `ballImpact()` method
The `ballImpact()` method is used to check whether a ball will impact on actor during a slice of time (between one frame of animation and the next), returning either `None` or a `Some(Impact)`. It takes the ball in question, an actor, and a length of time as parameters. The `Impact` that it may return consists of the actor impacted on (that would just be the `actor` parameter), the point of impact, the surface normal vector of the impact (which is needed to calculate how the ball will bounce), and the time at which the impact will occur (a number between `0` and the `time` parameter).

Fill this method in by doing the following:
1. Compute a `LineSegemnt` of the ball's motion during this slice of time: the first point is the ball's current location, and the second is the ball's location if it were allowed to move unchecked (using the same formula as before, `new location = old location + velocity × time`). You will use this `LineSegment`'s intersect methods to check for a collision with the actor.
2. Match on the actor. Depending on whether it's a `Wall` or a `Bumper`, check whether the `LineSegment` you computed for the ball intersects with the wall's `LineSegment`' or the bumper's `Circle`.
3. If there is an intersection (the intersect method returns `Some(Point)`), then you will need to compute an `Impact` for this collision. You already know the actor impacted on and the point of impact; for the normal, when the actor is a `Wall` you can use its `LineSegment`'s `normal` method, and when it's a `Bumper`, the normal is just `impact point - circle center point`. For the time of collision, use `(impact point - ball initial point).length / (ball velocity).length`.
4. Your method should return `None` if there was no impact point, and a `Some(Impact)` otherwise.

## `findBallImpacts()` method
The `findBallImpacts()` method is intended to detect all collisions that a ball will make with other actors during a
single time slice. It takes a ball and a time as parameters and returns a `Seq[Impact]`. A `Seq` is pretty much the same thing as an `Array`, the main difference being that it is immutable; it is much more common to use `Seq`s than `Array`s in Scala. Since you've already coded the `ballImpact()` method, this will be pretty easy: you just need to map each `Actor` in the field's `actors` to its `Impact` with the ball, if any.

You don't want to use your basic `actors.map()` method, though: since on any given time slice, the ball will not be impacting on most of the actors, using `actors.map()` would give you a ton of `None`s:
> Using `actors.map()`:
> 
> `    [None, None, None, Some(Impact(...)), None, None, None, Some(Impact(...))]`

Instead, you should use `actors.flatMap()`, which is called the same way but flattens it down to just the ones that contain values:
> Using `actors.flatMap():`
>
> `    [Impact(...), Impact(...)]`


Fill in the body of `findBallImpacts()` call `actors.flatMap()`, mapping the field actors to the ball's impacts on them. (This method should be very short!)

## `findNextBallImpact()` method
The method you just implemented, `findBallImpacts()`, finds *all* impacts the ball could make with other actors during the current time slice. Most of the time there will be zero of these, sometimes there will be one — and occasionally there could be more than one, for example if there are two walls that meet in a corner and the ball is about to cross them there. The point of this method is to find the *first* impact that the ball will make (the one with the smallest time value).

Another job that `findNextBallImpact()` has is to make sure that ball isn't double-colliding on the same actor immediately. When the ball bounces off a wall, we will set its location to the point of impact on the wall, but we don't want it to falsely detect that repositioning as a second collision. The `findNextBallImpact()` method takes an `Option[Actor]` as parameter, of the actor that the ball has immediately just bounced off of (if any); your code will need to make sure that it doesn't register another impact on this same actor.

Fill in `findNextBallImpact()` to do the following:
1) Call `findBallImpacts()` to get a `Seq` of all impacts the ball could make during this time slice.
2) Use `.filter()` to filter out any impacts on the `lastBounced` actor, if there is one (the easiest way to do this is to use `lastBounced.contains()` to check whether it holds the current actor we're deciding whether to filter).
3) Sort the impacts by time (`.sorted(Ordering.by(...))`).
4) Return the *first* `Impact` if there is one; `.headOption` will do this for you (it returns a `Some` of the first element in the `Seq` if there is one, otherwise `None`).

## `updateActor()` again
You're nearly there! Now that you have code in place to detect the next impact (if any), you just need to incorporate this in `updateActor()`. Your new version should, when the actor is a ball:
1. Check for an impact by calling `findNextBallImpact()`.
   1. If there is none, then proceed as before (`new location = old location + velocity × time`).
   2. If there is an impact, then you will need to bounce the ball. Make a new `Ball` object representing the ball's state at the instant of the bounce: its new location should be the point of impact and its velocity should be the result of reflecting the ball's velocity about the impact normal (use the `Vector.reflect()` method). Then recursively call `updateActor()` again with this new ball so that we can check for another impact during the same time slice: use `time - impact time` for the new time value, and `Some(impacted actor)` for `lastBounced` so that we don't double-bounce immediately.

## Testing your code
If all the above is done correctly, then all tests from "Part 2: TestField" should now pass. To admire your work, you can increase the numbers of balls, walls, and bumpers in the field by adjusting the initialization of `actors` near the top of `Field`.