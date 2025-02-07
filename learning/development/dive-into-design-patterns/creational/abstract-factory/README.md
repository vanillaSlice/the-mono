# Factory Method

## What?

Lets you produce families of related objects without specifying their concrete classes.

## Pros

* Avoid coupling between object creation and other logic.
* Can move object creation to a single place which adheres to the single responsibility principle.
* Can introduce new types which adheres to the open/closed principle.

## Cons

* Code can become a little more complicated with more subclasses that need to implement the pattern.
