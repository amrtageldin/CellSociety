# Cell Society Design Plan
### Team Number: 9
### Names: Reya Magan, Amr Tagel-Din, Luke Josephy, Evelyn Cupil-Garcia


## Design Overview
For our implementation of Cell Society, we are planning
to use a Model-View-Controller approach with abstraction 
to implement each of the different cell automaton applications.
Our view will be the starting point of our application such that
users will be able to load a game file to play one of the game options.
This file will then be sent into our controller for further parsing.
Within the controller we will first parse the .csv file to create
the grid for our game with the correct initial "live" and "dead" cells.
Then, we would parse through the .sim file to find the type of game. Since we
are planning to have an abstract game class and have each game extend it, we could call the
game that is found in the .sim file. We would then start the animation for this game,
and have an abstract rules class (from which the relevant rules for this game would be loaded up)
constantly checking through each cell to update its state. This state would be present in the model,
and we would send this data to the view for constant display.


## Design Details

Here is a graphical look at my design:

![This is cool, too bad you can't see it](images/online-shopping-uml-example.png "An initial UI")

made from [a tool that generates UML from existing code](http://staruml.io/).


## Design Considerations

#### Design Issue #1: How should we deal with all of the rules within the game

 * Alternative #1: We can have an abstract rules class that has an abstract executeRule method.
We can have all of the possible rules extend this class (one alive neighbor, three alive neighbors, probability rules etc.) Each rule would have their own executeRule method that does the correct
rule execution for the game.


 * Alternative #2: We can have an abstract rules class with the same abstract executeRule method as Alternative #1. However, instead of having every rule exist as its own class, we could have different classes
for each type of rule (conditional, probability, time, etc). Since the rules within these types are fairly similar, we could just split them into methods within the type classes. We could use reflection to determine which of these classes to call.


 * Trade-offs: Extending different types of rules and then having methods for each rule might not be the best design. It could make each type class really long, and could cause a decent amount of repeated code. It could also be too specific and hard to extend if we get a totally different type of rule introduced.  If we have each rule in its own class, we can easily add more and keep it fairly easy to follow. It could get long, so maybe we could organize them into packages for better readability. We could also have a basic rules class that each game type extends, with common methods present in the superclass.


#### Design Issue #2

 * Alternative #1

 * Alternative #2

 * Trade-offs



## User Interface

Here is our amazing UI:

![This is cool, too bad you can't see it](images/29-sketched-ui-wireframe.jpg "An alternate design")

taken from [Brilliant Examples of Sketched UI Wireframes and Mock-Ups](https://onextrapixel.com/40-brilliant-examples-of-sketched-ui-wireframes-and-mock-ups/).


## Team Responsibilities

 * Team Member #1: Reya - working primarily on the backend, Controller connections between model and view. Setting up abstractions to allow extendability for different game and rule types.

 * Team Member #2: Evelyn - working primarly on the frontend, displaying grid mechanics for each game.

 * Team Member #3

 * Team Member #4


#### Proposed Schedule
