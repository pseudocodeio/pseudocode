# Pseudocode

Pseudocode is a live programming environment for pseudocode implemented by [Keshav Saharia](http://keshav.is). It started as a project for my advanced students and high school interns to develop a programming language, and has evolved into a useful teaching tool that we use at [TechLab Education](https://techlab.education). All of the code is open-source and well documented, so it should be quite easy to add new instructions, modify the parser, and extend the language.

The next step after pseudocode is a conventional program like Python or Java. I'm also the developer of [Pythonroom](https://pythonroom.com), a fun and easy way to teach Python in any classroom. 

## Overview of parsing and lexing

The steps of a parser

## Examples

- draw shapes
- infinite loops
- write if statements
- write if-else statements
- get mouse position and clicked

## Todo list

**everyone** - create examples in the `example` package as `My_Example.pseudo`

- [ ] Get all keywords into expression/Constant.java
- [ ] Formatting the editor
- [ ] Console for print output
- [ ] Add polygons (`draw a polygon from a, b to c, d to e, f`)
- [ ] String interpolation (`print "the value of the variable is $variable and I am at $x, $y"`)
- [ ] Draw an image from the web (`import "https://image.com/image.png" as xyz` ... `draw xyz at 300, 300`)
- [ ] Ensure all files are commented
- [√] Random numbers
- [√] Random color
- [ ] Commenting in the code (`// comment`) - add to Lexer.java, not to Parser.java
- [ ] Square root and absolute value (`square root of x`, `absolute value of x`)
- [√] Distance function (`distance from x, y to a, b`)
- [ ] Mesh with other
- [ ] Fix mouse clicked event
- [ ] Arbitrary RGB colors (`draw an rgb 1, 2, 3 square`) and hex codes*
- [ ] Parser tests - test individual parsing routines

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