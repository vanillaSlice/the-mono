# Singleton

## What?

Lets you ensure that a class has only one instance, while providing a global access point to this instance.

## Pros

* Ensures a class has a single instance.
* You gain global access to that instance.
* The object is initialised the first time it is requested.

## Cons

* It violates the Single Responsibility Principle by having two responsibilities.
* It can mask bad design.
* Requires special treatment in multi-threaded environments.
* It can be difficult to test.
