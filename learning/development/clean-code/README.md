# Clean Code: A Handbook of Agile Software Craftsmanship by Robert C. Martin

![Cover](./cover.jpg)

## Chapter One: Clean Code

* Clean code does one thing well.
* One broken window starts the process toward decay.
* Clean code reads like well-written prose.
* Code without tests, is not clean.
* Clean code can be read, and enhanced by a developer other than its original author.
* Clean code always looks like it was written by someone who cares.
* Clean code contains no duplication, is expressive, and has tiny abstractions.
* Leave the campground cleaner than you found it.

## Chapter Two: Meaningful Names

* Use intention-revealing names.
* Avoid disinformation.
* Make meaningful distinctions.
* Use pronounceable names.
* Use searchable names.
* The length of a name should correspond to the size of its scope.
* Avoid encodings.
* Avoid mental mapping.
* Classes and objects should have noun or noun phrase names like `Customer`, `WikiPage`, `Account`, or `AddressParser`.
* Methods should have verb or verb phrase names like `postPayment`, `deletePage`, or `save`.
* Don't be cute. Say what you mean. Mean what you say.
* Pick one word per concept.
* Don't pun.
* Use solution domain names.
* Use problem domain names.
* Add meaningful context.
* Don't add gratuitous context.

## Chapter Three: Functions

* Functions should be small.
* Indent level of a function should not be greater than one or two.
* Functions should do one thing. They should do it well. They should do it only.
* Functions that do one thing cannot be reasonably divided up into sections.
* One level of abstraction per function.
* Code should read top-down like a narrative.
* Bury switch statements away, for example, in abstract factories.
* Use descriptive names.
* Zero is the ideal number of arguments. Followed by one and two. Avoid three or more.
* Avoid adding output information to arguments.
* Avoid flag arguments.
* Create argument objects.
* Have no side effects.
* Prefer exceptions to returning error codes.
* Extract try/catch blocks.
* Don't repeat yourself.
* Functions don't come out this way at the start. We massage them after a rough draft.

## Chapter Four: Comments

* Comments do not make up for bad code.
* Explain yourself in code.
* Good comments include: legal comments, informative comments, explanations of intent, clarifications,
warnings of consequences, TODO comments, amplifications, and docs in public APIS.
* Bad comments include: mumbling, redundant comments, misleading comments, mandated comments, journal
comments, noise comments, scary noise, position markers, closing brace comments, attributions, commented-out
code, HTML comments, nonlocal information, too much information, inobvious connections, function headers,
and docs in nonpublic code.

## Chapter Five: Formatting

* Teams should agree on a single set of formatting rules.
* Follow the newspaper metaphor, the class name should act as the title, we should get more details as we
read down the page.
* Variables should be declared as close to their usage as possible.
* Declare dependent functions close together, the callee under the caller.

## Chapter Six: Objects and Data Structures

* Objects hide their data behind abstractions and expose functions that operate on that data.
* Data structures expose their data and have no meaningful functions.
* The *Law of Demeter* says that a module should not know about the innards of the object it manipulates.
* Avoid creating a hybrid of objects and data structures.

## Chapter Seven: Error Handling

* Use exceptions rather than return codes.
* Use unchecked exceptions.
* Don't return null.
* Don't pass null.

## Chapter Eight: Boundaries

* Avoid accepting or returning interfaces like `Map` in public APIs. Use specific types!
* Write learning tests when using a third-party API.
* Create interfaces for code that doesn't exist yet.

## Chapter Nine: Unit Tests

* Three laws of TDD:
  * You may not write production code until you have written a failing unit test.
  * You may not write more of a unit test than is sufficient to fail, and not compiling is failing.
  * You may not write more production code than is sufficient to pass the currently failing test.
* Test code is just as important as production code.
* Use the *BUILD-OPERATE-CHECK* pattern when developing tests.
* One assert per test, if possible.
* Single concept per test.
* Clean tests should be:
  * Fast
  * Independent
  * Repeatable
  * Self-validating
  * Timely

## Chapter Ten: Classes

* Classes should be small!
* The Single Responsibility Principle states that a class or module should have one, and only one, reason to change.
* The Open-Closed Principle states that classes should be open for extension but closed for modification.
* The Dependency Inversion Principle states that classes should depend on abstractions, not concrete details.

## Chapter Eleven: Systems

* Separate construction of objects from use.
* *Dependency Injection* is a mechanism for doing this. It involves passing dependencies to classes.
* Scale-up by separating concerns. Separate cross-cutting concerns.
* Use standards wisely, when they add demonstrable value.
* Use the simplest thing that can possibly work.

## Chapter Twelve: Emergence

* Kent Beck's four rules of *Simple Design*:
  * Runs all the tests.
  * Contains no duplication.
  * Expresses the intent of the programmer.
  * Minimises the number of classes and methods.

## Chapter Thirteen: Concurrency

* Keep your concurrency-related code separate from other code.
* Limit the access of any data that may be shared.
* Use copies of data.
* Threads should be as independent as possible.
* Know your library.
* Know your execution models:
  * *Producer-Consumer*: The producer puts some work on a queue that the consumer completes.
  * *Readers-Writers*: Writers can't write if a reader is currently reading the data.
  * *Dining Philosophers*: Workers must acquire multiple resources before doing the work, or give up all resources.
* Keep synchronized sections small.

## Chapter Fourteen: Successive Refinement

* An example of refining a module.
* It isn't enough to get code working. The next step is to make it clean!

## Chapter Fifteen: JUnit Internals

* An example of refactoring a JUnit class.

## Chapter Sixteen: Refactoring `SerialDate`

* An example of refactoring the `SerialDate` class.

## Chapter Seventeen: Smells and Heuristics

* Comments:
  * Inappropriate Information
  * Obsolete Comment
  * Redundant Comment
  * Poorly Written Comment
  * Commented-Out Code
* Environment:
  * Build Requires More Than One Step
  * Tests Require More Than One Step
* Functions:
  * Too Many Arguments
  * Output Arguments
  * Flag Arguments
  * Dead Function
* General:
  * Multiple Languages in One Source File
  * Obvious Behaviour Is Unimplemented
  * Incorrect Behaviour at the Boundaries
  * Overridden Safeties
  *
