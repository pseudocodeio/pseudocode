# Pseudocode

Pseudocode is a live programming environment for writing natural language programs implemented by [Keshav Saharia](http://keshav.is) and maintained by the instructors/interns at [TechLab Education](https://techlab.education). 
It is designed to be a fun introduction to typed programming languages, without the burden of syntactical correctness or error messages. 

**Keshav will add GIF here**

There is no "run" button and no error messages - code is executed as you type it, and instructions that cannot be understood are ignored. After teaching with pseudocode, we teach Python with [Pythonroom](https://pythonroom.com) and Java with the [APCS graphics](http://apcs.io) library. 

## Getting started

For Mac OSX, the easiest way to get started is to download the [Pseudocode application](https://github.com/MAC_APP_URL) and copy it into your Applications folder.

You can also download a [runnable JAR file](https://github.com/pseudocodeio/pseudocode/files/384411/Pseudocode.zip) that will work on any operating system with a Java runtime environment.

## Features

### Drawing shapes

You can draw multiple shapes including circles, squares, rectangles, ovals, and polygons. In addition, the many attributes of these shapes can be changed according to your preference.

```
draw a circle
create a square
place a circle
put a rectangle
```

![picture of a circle](https://cloud.githubusercontent.com/assets/15526754/17146861/e7ca37da-5314-11e6-89df-c77c9d949f48.png)

You can position these shapes 

```
draw a circle at 100 100
draw a circle at the center
```

Their attributes are width, height, radius, size, diameter

```
draw a circle at 100 100 with a radius of 50
draw a square at 100 100 with a width of 50 and height of 50 
```

You can color shapes

```
draw a red circle
draw a green rectangle
```

You can draw polygons with predefined positions
```
draw a polygon from 0, 0 to 100, 100, 0, 50 to 0, 0
```

### Drawing images

You can draw images by giving a url or file path

```
draw an image "URL"
draw an image "FILE_PATH"
```

Images have the same attributes as shapes

```
draw an image "URL" at 100 100 with a size of 50
draw an image "URL" at 100 100 with a width of 50 and height of 50
```

### Colors

You can use preset colors such as blue, green, or light green

```
draw a red circle
```

You can use RGB values

```
draw a rgb 255 0 0 circle
```

Your can use Hex codes

```
draw a #fffffff circle
```

You can use random colors

```
draw a randomly colored circle
draw a random color circle
```

You can set the background to preset colors, RGB values, or hex codes

```
background to green
set background to rgb 255 255 255
set the background to #fffffff
```

### Mouse Functions
You can find the x and y positions of the mouse

```
set x to mouse x
set y to mouse y
```

With shapes, you can set their position to the mouse's position

```
draw a circle at the mouse
draw a circle at mouse x mouse y
```

**add mouse clicked**


### Variables

You can create variables and assign values to them

```
set x to 1
set y to x
```

You can print variables within text

```
set x to 5

print "The dog jumped over the quick brown fox by #x meters"
```


### Random Numbers

You can set random numbers with undefined ranges

```
set w to random number
```

You can set random numbers with a set maximum and a minimum of zero

```
set x to random number to 100
```

You can set numbers with predefined ranges

```
set y to random number between 50 and 100
set z to random number from 100 to 150
```

### Forever loops

You can create infinite loops which can run code forever

```
forever draw a small circle at the mouse
repeatedly draw a big circle at 100 100
always draw a circle at 50 50
```

### Operators

You can add, subtract, multiply, or divide numbers

```
set w to 1 plus 2
	set w to 1 + 2
set x to 4 minus 2
	set x to 4 - 2
set y to 3 times 3
	set y to 3 * 3
set z to 5 divided by 1
	set z to 5 / 1
```

You can invert numbers (change their sign)

```
set x to invert 1
set x to reverse 2
```

You can increase numbers by predefined amounts

```
increment x by 1
increase y by 5
change z by 7
```

You can decrease numbers by predefined amounts

```
decrement x by 3
decrease y by 6
```

You can compare numbers
```
1 is 1 (true)
	1 == 1
2 is not 2 (false)
	2 != 2
1 greater than 2 (false)
	1 > 2
2 is less than 3 (true)
	2 < 3
4 greater than or equal to 4 (true)
	4 ≥ 4
6 is less than or equal to 5 (false)
	6 ≤ 5
```

You can use multiple operators at the same time

```
1 is 1 or 2 is not 2 (true)
2 < 4 and 4 != 4 (false)
```

You can find the absolute value of a number

```
set y to absolute value of -1
set f to absolute value of g
```

You can find the square root of a number

```
set z to square root of 4
set a to square root of b
```

You can find the distance between two points

```
set x to distance from 100,100 to 500,500
set z to distance from a, b to c, d
```

### If and if-else statements

You can use if statements with logic operators creating a condition

```
if x is y
	draw a circle at 100 100
```

You can use if-else statements so that code can be run even if your condition is not true

```
if x is less than y 
	draw a green square at 50 50 
else 
	draw a blue sqaure at 100 100
	
if x > y 
	draw an orange circle at 50 50 
otherwise 
	draw a green circle at mouse
```

You can also compound multiple conditions with else if statements

```
if x is y 
	draw a square 
if x > y 
	draw a circle 
else 
	draw a green square
```

### Printing

You can print values

```
print x
```

### Waiting
You can pause your program for a certain amount of time

```
sleep 100
wait 100
delay 100
pause 100
```

### Functions **Check for correct syntax**
You can create functions with "to" and set parameters with "with"
**Not sure about the exact syntax**
```
to functionName with x
	print x
```

You can set multiple parameters

```
to functionName with x and y
to functionName with v, w
```

You can run functions with "do"
```
do functionName with 1
do functionName with 3, 4
```

## Examples

- draw shapes
- infinite loops
- write if statements
- write if-else statements
- get mouse position and clicked

## Todo list

**everyone** - create examples in the `example` package as `My_Example.pseudo`

- [√] Get all keywords into expression/Constant.java
- [√] Formatting the editor
- [√] Console for print output
- [√] Add polygons (`draw a polygon from a, b to c, d to e, f`)
- [√] String interpolation (`print "the value of the variable is #variable and I am at #x, #y"`)
- [√] Draw an image from the web (`import "https://image.com/image.png" as xyz` ... `draw xyz at 300, 300`)
- [ ] Ensure all files are commented
- [√] Random numbers
- [√] Random color
- [√] Commenting in the code (`// comment`) - add to Lexer.java, not to Parser.java
- [√] Square root and absolute value (`square root of x`, `absolute value of x`)
- [√] Distance function (`distance from x, y to a, b`)
- [ ] Mesh with other
- [ ] Fix mouse clicked event
- [√] Arbitrary RGB colors (`draw an rgb 1, 2, 3 square`) and hex codes*
- [ ] Parser tests - test individual parsing routines
- [ ] Allow images to be objects
- [ ] Allow gifs to be drawn
* in the Window.java library

## Future todo list

- [ ] Lists
- [ ] Functions

```
to draw a triangle at x, y,
	...
	
draw a triangle at x, y
```

```
to add one to x,
	set x to x + 1
	increment x
```

- [ ] Simple class

```
a ball has an x, y

when a ball is created,
	set x to a random number
	set y to a random number

when a ball is told to draw,
	draw a red circle at ball's x, ball's y

set b to a new ball
tell b to draw
```

## Mesh

- When you join the mesh, asks for your name
- Once you're in the mesh, then other people can access your variables
- Should be completely transparent, no need to write/read explicitly from the mesh

```
draw a green circle at keshav's x, keshav's y
```
