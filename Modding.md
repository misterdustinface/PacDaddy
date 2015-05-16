## Modding

All [Features](Adding-Features.md) have access to the PacDaddyGame class; therefore, they have access to all public attributes and methods of said class.

Think of the PacDaddyGame as a physical arcade cabinet for PacMan.

* PacDaddyGame
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

* PacDaddyMainLoop: The main loop for the game.  Add main-loop specific functions to it with:
  * addFunction(VoidFunctionPointer function)

* GameAttributes: In this case, these are the attributes of the PacDaddyGame.  Examples of attributes that the PacDaddyGame could have are score, lives, fps, updates per second, etc.  It also happens to be a PacDaddyAttributeReader.
  * void setAttribute(String name, Object value)
  * Object getValueOf(String attributeName)
  * String[] getAttributes()

* FunctionDispatchCommandProcessor: In this case, this is a method of dispatching commands by name for the PacDaddyGame.  Examples of commands that the PacDaddyGame could have are play, pause, joystick up, etc.  It also happens to be a PacDaddyInput.
  * void addCommand(String command, VoidFunctionPointer implementation)
  * void sendCommand(String command)
  * String[] getCommands()

* PactorController: Sends commands to a Pactor, which is the Game-Object of pacman.
  * void setPactor(Pactor pactor)
  * void sendCommandToPactor(String command)
  * String[] getPactorCommands()

* PacDaddyWorld: The world in which all Pactors interact.  It has strict regulations which cause the Pactors to act in a manner which replicates PacMan.  It also happens to be a PacDaddyBoardReader.
  * void loadFromString(String worldstring)
  * void addTileType(String name)
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
  * boolean doesPactorHaveAttribute(String pactorname, String attribute)
  * GameAttributes getWorldInfoForPactor(String name)
  * boolean canPactorMoveInDirection(String pactorName, String direction)
  * String[] getPactorNames()
 
* Pactor
  * void setOnCollisionFunction(PactorCollisionFunction ON_COLLISION)
  * void setAttribute(String name, Object value)
  * Object getValueOf(String attributeName) 
  * String[] getAttributes()
  * void learnAction(String action, VoidFunctionPointer implementation)
  * void forgetAction(String action)
  * void performAction(String action)
  * Set<String> getActions()

 
  

