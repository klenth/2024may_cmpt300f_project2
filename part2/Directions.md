# Part 2 directions

Part 1 was just a warm-up to give you a little practice working with `Option` objects. In Part 2, you're going to fill
in some pieces in a program that simulates balls bouncing around in a field with a number of obstacles.

<img src="animation.gif">

Most of the code is already written for you; you'll just need to fill in some of the pieces.

# Step 0: fill in the `Point` and `Vector` case classes
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
> new location = old location + velocity × time

...which is easily calculated using the operator overloads you defined in the last step.

Fill in `updateActor()` so that, for a ball, it uses the formula above to calculate the new location of the ball (the
ball's velocity should remain unchanged), and for any other kind of actor, it just returns the actor back. Then run the
app and check that the balls move! How exciting.

## Testing your code
Run the "Part 2: TestField" tester. Most of the tests will still fail at this point, but if you've done this correctly,
the `2_updateActor_no_collisions` test should pass.

# Step 3: simple collisions
Adding collision detection is harder than you might think, but all the heavy lifting in math has already been coded for
you (mostly in the `LineSegment` class, that provides methods for checking whether two line segments intersect and
whether a line segment intersects with a circle). You will need to glue the pieces together by writing Scala code,
however!

## `findBallImpacts()`
The `findBallImpacts()` method is intended to detect all collisions that a ball will make with other actors during a
single time slice (between displaying one frame of the animation and the next). It takes a ball and a time as parameters
and returns a `Seq[Impact]`. A `Seq` is pretty much the same thing as an `Array`, the main difference being that it is
immutable; it is much more common to use `Seq`s than `Array`s in Scala. `Impact` is a case class representing a single
collision, with fields of which actor the ball collided with, what the point of impact was, what the normal vector of
the surface at that point is (if you're not familiar with normals, this is needed for determining the angle that the
ball will bounce at), and the time at which the impact occurs.

z