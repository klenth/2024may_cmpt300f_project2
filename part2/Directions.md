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

# Step 1: handle ball motion without collisions
