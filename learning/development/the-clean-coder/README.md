# The Clean Coder: A Code of Conduct for Professional Programmers by Robert C. Martin

![Cover](./cover.jpg)

## Chapter One: Professionalism

* Take responsibility
* Do no harm to function
  * QA should find nothing
  * You must know it works
  * Automated QA
* Do no harm to structure
* Work ethic
  * Know your field
  * Continuous learning
  * Practice
  * Collaboration
  * Mentoring
  * Know your domain
  * Identify with your employer/customer
  * Humility

## Chapter Two: Saying No

* Professionals are expected to say no
* The most important time to say no is when the stakes are highest
* Play adversarial roles with your manager to achieve the best possible outcome
* Healthy teams strive to find a way to say yes but you have to be unafraid to say no to get to the right yes
* Dropping professional disciplines (e.g. code quality) is not a way to solve problems

## Chapter Three: Saying Yes

* Say what you'll do. Mean it. Do it.
* Words and phrases to look out for which are signs of noncommitment:
  * need/should
  * hope/wish
  * let's
* Only commit to things that you have full control of
* If you are relying on other people, still commit to the parts you have control all
* Professionals look for creative ways to say yes
* Professionals aren't obscure or vague, they give a definite yes or no and highlight any uncertainties
* Don't "try", you either will or won't

## Chapter Four: Coding

* Be prepared; don't write code when you're tired or worried
* Avoid "the Zone" you can lose context here
* Don't be rude when interrupted
* Pairing can help with writer's block
* Debugging is important but try to reduce the time you do it by creating fewer bugs. Professionals create few bugs
* Know when to walk a away from a problem, you'll be surprised that you might solve it in the shower or on a drive
* Don't be late, provide updates, and don't rush
* Don't say you're done or change the definition of done
* Ask for help when you need it and help others
* It is your duty to mentor more junior engineers

## Chapter Five: Test Driven Development

* The three laws of TDD:
  * You aren't allowed to write any production code until you have written a failing unit test
  * You aren't allowed to write more of a unit test than is sufficient to fail (not compiling is failing)
  * You aren't allowed to write more production code that it sufficient to pass the current failing unit test
* Benefits
  * Certainty
  * Fewer defects
  * More courage to make changes and clean-up code
  * Acts as documentation

## Chapter Six: Practicing

* Practice in your own time, it is not on your employer to keep your skills sharp
* Do coding katas
* Contribute to open-source
* Code in different languages from your job

## Chapter Seven: Acceptance Testing

* Requirements between business and developers is often lost
* The more precise you make your requirements early on, the less they stay relevant as the system is implemented
* Estimates will have huge variance
* Defer precision as long as possible
* Make sure that ambiguity is removed from the requirements
* Professional developers have a single definition of done
  * Done means done
  * All code is written, all tests pass, QA and stakeholders have accepted
  * Automated acceptance tests meet all of the criteria
* Acceptance tests are for communication, clarity, and precision
* They should be automated
* Many tools exist for this e.g. Cucumber
* QA and stakeholders should write these tests but developers often do, make sure they aren't the ones implementing the feature
* Acceptance tests are not unit tests
* Unit tests are for programmers by programmers
* Acceptance tests are for the business for by the business

## Chapter Eight: Testing Strategies

* Teams should have a testing strategy
* QA should find nothing
* The test automation pyramid consists of:
  * Unit tests - written by programmers for programmers; should aim for 100% coverage
  * Component tests - witten by QA and the business; should aim for 50% coverage; mocks other components; against APIs
  * Integration tests - written by system architects; should aim for 20% coverage; tests that components are plumbed together; against APIs
  * System tests - written by architects; should aim for 10% coverage; tests entire system is plumbed together; against UIs
  * Manual exploratory tests - not automated; humans try to break the system

## Chapter Nine: Time Management

* Resist attending meetings that don't have immediate or significant benefit
* When the meeting gets boring, leave
* Teams should have an agenda and a clear goal
* You have a limited amount of focus time that you should use:
  * Make sure you get enough sleep
  * Limit caffeine
  * Make sure to recharge
  * Exercise
  * Be creatively stimulated
* Try the Pomodoro Technique
* Don't avoid things that are true priorities
* Be mindful of blind alleys and getting vested in an idea that you can't abandon
* Avoid creating messes and look to clean them, they have detrimental effects to companies

## Chapter Ten: Estimation

* Business likes to view estimates as commitments
* Developers like to view estimates as guesses
* Professionals don't make commitments unless they know they can achieve them
* Three numbers for estimation (PERT estimation):
  * Optimistic - if everything went right (1% chance of occurrence)
  * Nominal - estimate with the greatest probability
  * Pessimistic - if many things go wrong (1% chance of occurrence)
* Estimates should be agreed with a team e.g. using planning poker
