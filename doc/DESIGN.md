# Cell Society Design Final
### Names
Amr Tagel-Din, Reya Magan, Evelyn Cupil-Garcia, Luke Josephy

## Team Roles and Responsibilities

 * Team Member #1: Reya Magan

Involved with the backend. Implemented the Schelling and WaTor games, and pair programmed on the GameOfLife game.
Also implemented neighbor policies, as well as random cell configuration. Pair programmed the rule set up. Created the parsing mechanisms for the .sim and .csv files, and used reflection for both classes and methods to call the relevant ones.
Also refactored a lot to include consumers + abstract wherever possible. Refactored to make sure that instance variables were not accessed directly. Implemented error handling for the back-end. Implemented method reflection within
the rule structure to call relevant methods within Schelling and Fire games to set percent parameters.
(note: Amr and Reya did lots of Peer programming)


 * Team Member #2: Amr-Tagel Din 
   Involved with the backend. Implemented the Percolation and Fire games, and pair programmed on the GameOfLife game. Worked on neighbor policies (refactoring to reduce redundancy in those classes).
   Set up rule property files to read in ">, <, eq" symbols to call relevant Rules classes to automatically run classes to determine how a cell's state should change. Created a Grid class to hold the 2D array
   of cells, and to "hide" the data structure across all other classes. Used reflection for both classes and methods to call the relevant ones, and refactored a lot to include
   consumers + abstract methods wherever possible.
   (note: Amr and Reya did lots of Peer programming)

 * Team Member #3

 * Team Member #4


## Design goals
Our main design goal was to make our program closed to modification, but open to extension. 
We wanted to make each part of our application as abstract as possible, so if any changes were to be
added they could easily be implemented without having to change any major aspect of the code. 
We did this through creating abstractions/interfaces for all of our high-level classes.


#### What Features are Easy to Add

Our design choices made a lot of features simple to add. We were able to implement each game to full 
functionality by having the abstract CellSocietyModel, CellSocietyRules, and Rules classes. 
We also utilized the CellSocietyMovement interface well to deal with cell movement in the WaTor and 
Schelling games. We also created strong property resource files that allowed us to use reflection to 
run all of the required rules. Finally we had an abstract Neighbors class that allowed us to deal
with multiple kinds of neighbors.


## High-level Design
The view prompts the user to upload a sim and csv, which then prompts the controller to generate
a game and a grid. View then makes the controller step through the game. Each step calls upon the 
model of the game, which holds some of the logic of generating the next state (for example: if 0
stay 0, if 1 check the rules.). For some of the more advanced games the cellMovement class is used
to dictate these rules further. These checks are done using consumers.
If we need to check the rules, we use reflection to call upon an appropriate ruleStructure, which
reads in a property file and interprets the methods and comparisons that dictate the next state of
the cell. the next state of the cell is then returned. When every cell's next state is found, every
cell updates towards its next state.

#### Core Classes

CellSocietyRules
CellSocietyModel
CellSocietyNeighbors
CellSocietyMovement
CellSocietyView
CellSocietyController

## Assumptions that Affect the Design

A user does not have to use the matching sim for a csv in order to run a game
Only square cells will be considered mathematically in the backend

#### Features Affected by Assumptions
By only making square cells, we were able to heavily expand upon the neighbors set of classes since
we had a more concrete basis; by choosing to prioritize the square class we were able to have this
specific cell shape but extremely flexible, rather than having multiple relatively rigid cell shapes.

More grid combinations are now possible by purposefully leaving out sim and csv matching error 
handling.

## Significant differences from Original Plan

While we initially planned to have the cellSocietyGrid have cells in the shape of others squares
(such as hexagons, triangles, etc.) this plan, after much painstaking math and considerations,
was left behind as the backend team felt that working so heavily into this feature would take away
from the program as a whole and the flexibliity of its other features.

We also did not initially plan to have CellMovement be an interface, but this interface was created
by Reya realizing that this type of CellSociety with "moving cells" required something to dictate that
movement to decrease redundancy.

## New Features HowTo

If we wanted to add a new game to this design, it would be fairly straightforward to do so! 
First, we would need to set up the data files for this game (the .sim and .csv files). 
We would populate these with any relevant parameters and initial state information. 
Then, we would need to create a game model class for this game that would extend the abstract game 
model class and be called using reflection within the controller. We would also initialize its own 
CellSocietyRules class as well. We would need to create its state properties file as well its rules 
file. Finally, if this game were to involve cellular movement such as Schelling and WaTor did, then 
we would also add a class that implemented the CellSocietyMovement interface.

If we wanted to add a new neighbor configuration, this would be very simple. We would set it up as 
an extended class from the abstract CellSocietyNeighbors class. We would implement the 
generateNeighbors method for this new type, and then add the title of this class to .sim files that 
want to use this type of Neighbor configuration.


#### Easy to Add Features
New types of neighbors
New types of rules
New games, within pre-existing game classes or as their own game
new Initial cell state setups


#### Other Features not yet Done
Different shape for grids
edge policies
