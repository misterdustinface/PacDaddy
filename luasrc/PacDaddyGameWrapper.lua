local public = {}

local game = require("luasrc/PacDaddyGameSingleton")

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

--local function setPlayerPosition(this, row, col)
--    game.world:getPactor("PLAYER"):getTileCoordinate().row = row
--    game.world:getPactor("PLAYER"):getTileCoordinate().col = col
--end

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