local public = {}

local game = require("luasrc/PacDaddyGameWrapper/PacDaddyGameSingleton")

local function addComponentToGame(this, name, implementation)
    game:addComponent(name, implementation)
end

local function startGame(this)
    game:start()
end

local function quitGame(this)
    game:quit()
end

local function sendCommand(this, command)
    this.inputProcessor:sendCommand(command)
end

local function getCommands(this)
    return this.inputProcessor:getCommands()
end

local function getValueOf(this, attribute)
    return this.attributeReader:getValueOf(attribute)
end

local function getAttributes(this)
    return this.attributeReader:getAttributes()
end

local function getTiledBoard(this)
    return this.boardReader:getTiledBoard()
end

local function getTileNames(this)
    return this.boardReader:getTileNames()
end

local function getAttributeReaderAtTile(this, row, col)
    return this.boardReader:getAttributeReaderAtTile(row, col)
end

local function getSingleton(this)
    return game
end

--local function setPlayerPosition(this, row, col)
--    game.world:getPactor("PLAYER"):getTileCoordinate().row = row
--    game.world:getPactor("PLAYER"):getTileCoordinate().col = col
--end

-- TODO grant write access to PacDaddyGame java
-- TODO grant access to FunctionDispatchCommandProcessor java (in PacDaddyGame) [addCommand, sendCommand, getCommands]
-- TODO grant access to GameAttributes java (in PacDaddyGame) [setAttribute, getValueOf, getAttributes]
-- TODO grant access to PacDaddyMainLoop java (in PacDaddyGame) [addFunction]
-- TODO grant access to PacDaddyWorld java (in PacDaddyGame) [addPactor, removePactor, getPactor, addTileType, loadFromString]
-- TODO grant access to PactorController java (in PacDaddyGame) [setPactor, sendCommandToPactor, getPactorCommands]
-- TODO grant access to Pactor java [performAction, learnAction, forgetAction, getActions, setAttribute, getValueOf, getAttributes, getTileCoordinate]

public.boardReader     = game:getBoardReader()
public.inputProcessor  = game:getInputProcessor()
public.attributeReader = game:getGameAttributeReader()
public.addComponent    = addComponentToGame
public.start           = startGame
public.quit            = quitGame
public.sendCommand     = sendCommand
public.getCommands     = getCommands
public.getValueOf      = getValueOf
public.getAttributes   = getAttributes
public.getTiledBoard   = getTiledBoard
public.getTileNames    = getTileNames
public.getAttributeReaderAtTile = getAttributeReaderAtTile

return public