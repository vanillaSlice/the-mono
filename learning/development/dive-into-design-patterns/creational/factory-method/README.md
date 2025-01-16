# Factory Method

## What?

Provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created.

## Pros

* Avoid coupling between object creation and other logic.
* Can move object creation to a single place which adheres to the single responsibility principle.
* Can introduce new types which adheres to the open/closed principle.

## Cons

* Code can become a little more complicated with more subclasses that need to implement the pattern.
