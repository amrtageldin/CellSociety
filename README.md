Cell Society
====

This project implements a cellular automata simulator.

Names:
Reya Magan,
Amr Tagel-Din,
Evelyn Cupil-Garcia,
Luke Josephy

### Timeline

Start Date: 10/13

Finish Date: 11/2

Hours Spent: ~50 each

### Primary Roles
Reya Magan - Involved with the backend. Implemented the Schelling and WaTor games, and pair programmed on the GameOfLife game.
Also implemented neighbor policies, as well as random cell configuration. Pair programmed the rule set up. Created the parsing mechanisms for the .sim and .csv files, and used reflection for both classes and methods to call the relevant ones.
Also refactored a lot to include consumers + abstract wherever possible. Refactored to make sure that instance variables were not accessed directly. Implemented error handling for the back-end. Implemented method reflection within
the rule structure to call relevant methods within Schelling and Fire games to set percent parameters.

Amr-Tagel Din - Involved with the backend. Implemented the Percolation and Fire games, and pair programmed on the GameOfLife game. Worked on neighbor policies (refactoring to reduce redundancy in those classes).
Set up rule property files to read in ">, <, eq" symbols to call relevant Rules classes to automatically run classes to determine how a cell's state should change. Created a Grid class to hold the 2D array
of cells, and to "hide" the data structure across all other classes. Used reflection for both classes and methods to call the relevant ones, and refactored a lot to include
consumers + abstract methods wherever possible.

### Resources Used
https://en.wikipedia.org/wiki/Conway's_Game_of_Life

https://en.wikipedia.org/wiki/Forest-fire_model

https://en.wikipedia.org/wiki/Schelling's_model_of_segregation

https://en.wikipedia.org/wiki/Wa-Tor

https://courses.cs.duke.edu//fall21/compsci307d/assign/team/02_cellsociety/PercolationCA.pdf

http://golly.sourceforge.net/Help/Algorithms/QuickLife.html#nontotal

### Running the Program

Main class: Main.java

Data files needed: Depending on which game to be implemented, you can select
the relevant folder and select both a .sim and a .csv file to run for the game.

Features implemented:

Back-End:
- All games with full functionality
- Neighbor policies (full, up and down, left and right, diagonal, rectangle)
- Different types of cell set up configurations (read from CSV file, randomly generated)
- Error-Handling



### Notes/Assumptions

Assumptions or Simplifications:

Assumptions: A user does not have to use the matching sim for a csv in order to run a game; while they must be the same game, it allow for more interesting designs for the user to match the rules and grids of different samples

For this project, we simplified by using a 2d array as a Grid to assume cell shapes were squares; this allowed us to focus more on the grid and what we could do with it than figure out the math behind different shapes

Interesting data files:

game_of_life/random.sim, game_of_life/random.csv : randomly generates initial cell states

wa_tor/random.sim, wa_tor/random.csv : randomly generates initial cell states

schelling_segregation/five-reproduction-three-energy.sim and .csv: uses LeftRightNeighbors only

Fire_Spread/volcano.csv + sim : looks like an actual volcano erupting!

Known Bugs:

One bug that exists is that there are some sequence of "error" files that do accurately display an error,
but sometimes this error starts infinitely showing up, at which point we need to quit the whole application and restart.
We tried debugging this but decided to prioritize design decisions given the time constraint.

Also, Adding more than 2 games results in a screen that jumps around in size and seems unstable; generally having more than one game is not very stable at the moment


Noteworthy Features:

Using property files we have essentially abstractified our ruleStructure so much that they are essentially useless now, and only the abstract version is important. While we decided to keep it for the sake of future flexibility and in case a future project required something these didn't, our system of reading in methodrules and rules from a property file was very efficient and simplified lots of the rule process

We heavily implemented consumers, reflection (both class and method), and interfaces to reduce duplicated code and allow for extendability and abstraction.

Neighbors was created very flexibly, and in a way that allows you to very easily create new neighbor combinations and mix and match from others; for example, full combines diagonal and rectangle (which combines topBottom and leftRight)

### Impressions
From the backend perspective, we thought this project was very rich in opportunity to test new skills
from class. Reflection, Consumers, Utilizing property files and good abstraction became necessary, and overall
most of the requests were a good balance of difficult yet reasonable. While personally we felt that implementing
some features (such as a boundless grid or hexagonal cells) were an unfairly difficult task in terms of math
more so than in terms of coding and were quite high of a reach, other requests like neighbor policies or
randomly setting up a cell were a great test of the flexibility of our code.

