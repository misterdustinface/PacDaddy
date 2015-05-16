## Modding

All [Features](Adding-Features.md) have access to the PacDaddyGame class; therefore, they have access to all public attributes and methods of said class.

This engine's FeatureLoader is built on top of LuaJ, so utilize its features.
This engine is built on top of LibD, so utilize its features.

Think of the PacDaddyGame as a physical arcade cabinet for PacMan.

### PacDaddyGame.  
It happens to be an Application from LibD.
* PacDaddyBoardReader getBoardReader()
* PacDaddyAttributeReader getGameAttributeReader()
* PacDaddyInput getInputProcessor()
* Object getWritable(String name)
* String[] getWritables()
  * "MAINLOOP", PacDaddyMainLoop
  * "ATTRIBUTES", GameAttributes
  * "INPUT_PROCESSOR", FunctionDispatchCommandProcessor
  * "PACTOR_CONTROLLER", PactorController
  * "WORLD", PacDaddyWorld
* void setMain(TickingLoop PROGRAM_MAIN)
* void addComponent(String name, Runnable runnableComponent)
* String[] getComponentNames()
* void start()
* void quit()
* void startComponent(String name)
* void stopComponent(String name)


### PacDaddyMainLoop: 
The main loop for the game.  Add main-loop specific functions to it.  It happens to be a TickingLoop from LibD (a Runnable).
* addFunction(VoidFunctionPointer function)
* void setUpdatesPerSecond(int UPS)
* void run()
* boolean isPaused()


### GameAttributes: 
In this case, these are the attributes of the PacDaddyGame.  Examples of attributes that the PacDaddyGame could have are score, lives, fps, updates per second, etc.  It also happens to be a PacDaddyAttributeReader.
* void setAttribute(String name, Object value)
* Object getValueOf(String attributeName)
* String[] getAttributes()


### FunctionDispatchCommandProcessor: 
In this case, this is a method of dispatching commands by name for the PacDaddyGame.  Examples of commands that the PacDaddyGame could have are play, pause, joystick up, etc.  It also happens to be a PacDaddyInput.
* void addCommand(String command, VoidFunctionPointer implementation)
* void sendCommand(String command)
* String[] getCommands()


### PactorController: 
Sends commands to a Pactor, which is the Game-Object of pacman.
* void setPactor(Pactor pactor)
* void sendCommandToPactor(String command)
* String[] getPactorCommands()


### PacDaddyWorld: 
The world in which all Pactors interact.  It has strict regulations which cause the Pactors to act in a manner which replicates PacMan.  It also happens to be a PacDaddyBoardReader.
* void loadFromString(String worldstring) 
  * A 2D array-string of integers to create a tiled map.
* void addTileType(String name)
  * Names for the tiles, ordered by integer value as an index.
* int[][] getTiledBoard()
* String[] getTileNames()
* int getRows()
* int getCols()
* void addPactor(String name, Pactor p)
* void removePactor(String name)
* Pactor getPactor(String name)
* int getRowOf(String name)
* int getColOf(String name)
* void setPactorSpawn(String name, int row, int col)
* void respawnPactor(String name)
* void respawnAllPactors()
* void setPactorSpeed(String name, float speed__pct)
* boolean isTraversableForPactor(int row, int col, String pactorname)
* void setTileAsTraversableForPactor(String tilename, String pactorname)
* GameAttributes[] getInfoForAllPactorsWithAttribute(String attribute)
  * Array of Info for Pactors with the given Attribute (in this case it is WorldInfo).
* boolean doesPactorHaveAttribute(String pactorname, String attribute)
* GameAttributes getWorldInfoForPactor(String name) 
  * Read-only information that the world has about the pactor, such as ROW, COL, DIRECTION, SPEED__PCT, and NAME, which cannot be accessed from the Pactor itself (except for NAME when a pactor is added to the world).
* boolean canPactorMoveInDirection(String pactorName, String direction)
  * UP, DOWN, LEFT, RIGHT are currently the only defined directions
* String[] getPactorNames()
 

### Pactor: 
The Game-Object of pacman.
* void setOnCollisionFunction(PactorCollisionFunction ON_COLLISION)
* void setAttribute(String name, Object value)
* Object getValueOf(String attributeName) 
* String[] getAttributes()
* void learnAction(String action, VoidFunctionPointer implementation)
* void forgetAction(String action)
* void performAction(String action)
* Set<String> getActions() 
  * UP, LEFT, DOWN, RIGHT, and NONE are already defined.

### VoidFunctionPointer: 
Exactly what you think it is.  Implement its interface:
* void call();
  

