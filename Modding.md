## Modding

All [Features](Adding-Features.md) have access to the PacDaddyGame class; therefore, they have access to all public attributes and methods of said class.

Think of the PacDaddyGame as a physical arcade cabinet for PacMan.

* PacDaddyGame
  * PacDaddyBoardReader getBoardReader()
  * PacDaddyAttributeReader getGameAttributeReader()
  * PacDaddyInput getInputProcessor()
  * Object getWritable(String name)
  * String[] getWritables()

* The writables of a PacDaddyGame are: 
  * "MAINLOOP", PacDaddyMainLoop
  * "ATTRIBUTES", GameAttributes
  * "INPUT_PROCESSOR", FunctionDispatchCommandProcessor
  * "PACTOR_CONTROLLER", PactorController
  * "WORLD", PacDaddyWorld

* PacDaddyMainLoop: The main loop for the game.  Add main-loop specific functions to it with:
  * addFunction(VoidFunctionPointer function)

* GameAttributes: In this case, these are the attributes of the PacDaddyGame.  Examples of attributes that the PacDaddyGame could have are score, lives, fps, updates per second, etc.
  * void setAttribute(String name, Object value)
  * Object getValueOf(String attributeName)
  * String[] getAttributes()

* FunctionDispatchCommandProcessor: In this case, this is a method of dispatching commands by name for the PacDaddyGame.  Examples of commands that the PacDaddyGame could have are play, pause, joystick up, etc.
  * void addCommand(String command, VoidFunctionPointer implementation)
  * void sendCommand(String command)
  * String[] getCommands()

* PactorController: Sends commands to a Pactor, which is the Game-Object of pacman.
  * void setPactor(Pactor pactor)
  * void sendCommandToPactor(String command)
  * String[] getPactorCommands()

* PacDaddyWorld: 

 
  

