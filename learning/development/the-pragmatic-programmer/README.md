# The Pragmatic Programmer by David Thomas and Andrew Hunt

![Cover](./cover.jpg)

## Chapter One: A Pragmatic Philosophy

* It's your life, you have agency
* Make changes in your job or change your job
* Take responsibility, provide options, don't make lame excuses
* Don't live with broken windows
* Be a catalyst for change
* Remember the big picture
* Make quality a requirements issue
* Invest regularly in your knowledge portfolio
  * Learn at least one new language every year
  * Read a technical book each month
  * Read non-technical books too
  * Take classes
  * Participate in local users groups and meet-ups
  * Experiment with different environments
  * Stay current
* Critically analyse what you read and hear
* Communication:
  * English is just another programming language
  * Know your audience
  * Know what you want to say
  * Choose your moment, make what you're saying relevant in time
  * Choose a style to suit your audience
  * Make it look good
  * Involve your audience
  * Be a listener
  * Get back to people
  * Build documentation in, don't bolt it on

## Chapter Two: A Pragmatic Approach

* Good design is easier to change than bad design
* DRY - Don't Repeat Yourself
  * Coincidences are not duplication - don't de-duplicate these
  * Don't duplicate code in comments
  * Duplication can occur between developers, ensure communication
  * Make it easy to reuse
* Orthogonality in computing refers to system independence
  * Changes in one system do not affect any of the others
  * Eliminate effects between unrelated things
  * Orthogonal systems are reusable
* There are no final decisions, build systems that can reverse decisions (e.g. database vendors)
* Forgo following fads
* Use tracer bullets to find the target
  * Make a simple E2E to prove that components can communicate successfully and prove out a feature
  * Users get to see something early
  * Developers build a structure to work in
  * You have an integration platform
  * You have something to demonstrate
  * You have a better feel for progress
  * It is not prototyping, tracer bullets are kept
* Prototype to learn
  * Ensure everyone knows this is throwaway code
* Program close to the problem domain (e.g. Cucumber)
* Estimate to avoid surprises
  * Ask how long people took to complete a similar project
  * Estimate subtasks
  * Give a range of numbers (best, worst, and likely cases)
* Iterate the schedule with code

## Chapter Three: The Basic Tools

* Keep knowledge in plain text
* Use the power of command shells
* Achieve editor fluency
* Always use version control
* Debugging
  * Fix the problem, not the blame
  * Don't panic
  * Write a failing test before fixing code
  * Read the damn error message
  * Rubber duck, talk people through the problem and you'll likely reveal a solution
  * It's unlikely to be a system fault, it's likely your application code
  * Don't assume you've fixed it, prove it
* Learn a text manipulation language

## Chapter Four: Pragmatic Paranoia

* You can't write perfect software
* Design with contracts, functions the have pre- and post-conditions
* Crash early, a dead program does less damage than a crippled one
* Use assertions to prevent the impossible, and leave them switched on in production!
* Finish what you start, release resources, avoid tight coupling
* Whatever allocates a resource should reallocate it i.e. free it up
* Act locally
* Take small steps, always
* Avoid fortune-telling

## Chapter Five: Bend, or Break

* Decoupled code is easier to
* Tell, don't ask (don't expose low-level details)
* Don't chain method calls
* Avoid global data
* If it's import enough to be global, wrap it in an API
* Programming is about code, but programs are about data (think about data transformations, inputs, and outputs)
* Don't hoard state; pass it around
* Don't pay inheritance tax, avoid inheritance
* Prefer interfaces to express polymorphism
* Delegate to services: "has a" trumps "is a"
