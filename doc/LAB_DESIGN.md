# Cell Society Design Discussion
### Team Number: 9 
### Names: Reya Magan, Amr Tagel-Din, Luke Josephy, Evelyn Cupil-Garcia


### Discussion Questions

 * __How does a Cell know what rules to apply for its simulation?__
    
   We are planning to have an abstract rules class that each rule type will extend. We are planning to have a model superclass that each game extends for its own model, this model class will call its respective rules class within the rule structure package. This rules class will only call the relevant rules for its own game.

 * __How does a Cell know about its neighbors?__
   We will have some sort of method within the model (checkNeighbors) that looks at diagonal, left, right, top, bottom neighbors within a 2D array depicting the grid.

 * __How can a Cell update itself without affecting its neighbors update?__

   We will go through the grid and apply the rules such that the cells next state will be its updated state depending on the rules. Then when every cell knows what it needs to update to next, we will update every cell to show its changes. This way, rules will not affect the cell until after every cell has been processed.
 * __What behaviors does the Grid itself have?__
   The Grid will be able to share its current and next state, and also set its current and next state (for each cell) dependent on rules.
 * __How can a Grid update all the Cells it contains?__
   We will have a cells class for each cell in the grid that will update its next state, and then after each cell's next state is updated the grid will set these next states as the current state for each cell.
 * __How is configuration information used to set up a simulation?__
The controller will read in a file and figure out if it is a .sim or a .csv file. Depending on which one it is, the relevant factory will be called to initialize the game type as well as the initial grid within the simulation.
 * __How is the GUI updated after all the cells have been updated?__
We will have a running animation that calls the step method within the controller every few seconds, this will continue to update the grid in response to the rules.

### Alternate Designs

#### Design Idea #1 - Setting up the grid

 * Data Structure #1 and File Format #1:
So we have a CSV file for the grid and we could read the grid into 2D array. We don't know reveal what type of array it is, we just know that it maps to cells in a grid.

 * Data Structure #2 and File Format #2:
If we have a file format that is not a CSV we would need to parse this in a different way (we can't use CSV readers), so we would need to set up some regular expression ways to parse the file to create a similar 2D array. 


#### Design Idea #2 - Setting up the game

 * Data Structure #1 and File Format #1:
We read in a .sim file and parse it to get the game type and store it in a string. This string can be used in reflection to call relevant game specific classes.

 * Data Structure #2 and File Format #2:
If the file is not a .sim format (maybe it is .txt), we would parse it in the same way, and store it in a string to be used in reflection to call specific game classes.


### CRC Card Classes
You can see our CRC Card Classes [here.](https://docs.google.com/document/d/1MysQGzZIPu0_Gq25SuUfejkT-pl-X0O_RQfOtxomkYE/edit)
