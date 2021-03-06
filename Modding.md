## Modding

All [Features](Adding-Features.md) have access to the PacDaddyGame class; therefore, they have access to all public attributes and methods of said class.

This FeatureLoader is built on top of LuaJ, so utilize its features.

This engine is built on top of LibD, so utilize its features.

Think of the PacDaddyGame as a physical arcade cabinet for PacMan.

##### Index
0. [PacDaddyGame](#pacdaddygame)
1. [PacDaddyMainLoop](#pacdaddymainloop)
2. [GameAttributes](#gameattributes)
3. [FunctionDispatchCommandProcessor](#functiondispatchcommandprocessor)
4. [PacDaddyWorld](#pacdaddyworld)
5. [Pactor](#pactor)
6. [PactorController](#pactorcontroller)
7. [VoidFunctionPointer](#voidfunctionpointer)

### [PacDaddyGame](#index)
It happens to be an Application from LibD.
* PacDaddyBoardReader getBoardReader()
* PacDaddyAttributeReader getGameAttributeReader()
* PacDaddyInput getInputProcessor()
* Object getWritable(String name)
* String[] getWritables()
  * "MAINLOOP", PacDaddyMainLoop
  * "ATTRIBUTES", GameAttributes
  * "INPUT_PROCESSOR", FunctionDispatchCommandProcessor
  * "WORLD", PacDaddyWorld
* void setMain(TickingLoop PROGRAM_MAIN)
* void addComponent(String name, Runnable runnableComponent)
* String[] getComponentNames()
* void start()
* void quit()
* void startComponent(String name)
* void stopComponent(String name)


### [PacDaddyMainLoop](#index)
The main loop for the game.  Add main-loop specific functions to it.  It happens to be a TickingLoop from LibD (a Runnable).
* addFunction(VoidFunctionPointer function)
* void setUpdatesPerSecond(int UPS)
* void run()
* boolean isPaused()


### [GameAttributes](#index)
In this case, these are the attributes of the PacDaddyGame.  Examples of attributes that the PacDaddyGame could have are score, lives, fps, updates per second, etc.  It also happens to be a PacDaddyAttributeReader.
* void setAttribute(String name, Object value)
* Object getValueOf(String attributeName)
* String[] getAttributes()


### [FunctionDispatchCommandProcessor](#index)
In this case, this is a method of dispatching commands by name for the PacDaddyGame.  Examples of commands that the PacDaddyGame could have are play, pause, joystick up, etc.  It also happens to be a PacDaddyInput.
* void addCommand(String command, VoidFunctionPointer implementation)
* void sendCommand(String command)
* String[] getCommands()


### [PacDaddyWorld](#index)
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
  * Read-only information that the world has about the pactor, such as "ROW", "COL", "DIRECTION", "SPEED__PCT", and "NAME", which cannot be accessed from the Pactor itself (except for "NAME" after a pactor has been added to the world).
* int getNumberOfPactorsWithAttribute(String attribute) 
* boolean canPactorMoveInDirection(String pactorName, String direction)
  * "UP", "DOWN", "LEFT", "RIGHT" are currently the only defined directions
* String[] getPactorNames()
 

### [Pactor](#index)
The Game-Object of pacman.
* void setOnCollisionFunction(PactorCollisionFunction ON_COLLISION)
* void setAttribute(String name, Object value)
* Object getValueOf(String attributeName) 
* String[] getAttributes()
* void learnAction(String action, VoidFunctionPointer implementation)
* void forgetAction(String action)
* void performAction(String action)
* Set<String> getActions() 
  * "UP", "LEFT", "DOWN", "RIGHT", and "NONE" have already been defined.


### [PactorController](#index)
Sends commands to a Pactor, which is the Game-Object of pacman.
* void setPactor(Pactor pactor)
* void sendCommandToPactor(String command)
* String[] getPactorCommands()
* VoidFunctionPointer wrapCommand(final String command)


### [VoidFunctionPointer](#index)
Exactly what you think it is.  Implement its interface:
* void call();
  

