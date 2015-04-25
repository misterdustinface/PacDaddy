require("luasrc/VoidFunctionPointer")

local inputProcessor   = GAME:getModifiableInputProcessor()
local pactorController = GAME:getModifiablePactorController()
local gameAttributes   = GAME:getModifiableAttributes()
local mainLoop         = GAME:getModifiableGameLoop()

local function getGameSpeed__ups()
    return gameAttributes:getValueOf("GAMESPEED__UPS")
end

local function shiftGameSpeed__ups(shiftamount__ups)
    gameAttributes:setAttribute("GAMESPEED__UPS", getGameSpeed__ups() + shiftamount__ups)
end

local function movePactorUp()
    pactorController:sendCommandToPactor("UP")
end

local function movePactorDown()
    pactorController:sendCommandToPactor("DOWN")
end

local function movePactorLeft()
    pactorController:sendCommandToPactor("LEFT")
end

local function movePactorRight()
    pactorController:sendCommandToPactor("RIGHT")
end

local function pause()
    gameAttributes:setAttribute("IS_PAUSED", true)
    mainLoop:setUpdatesPerSecond(0)
end

local function play()
    gameAttributes:setAttribute("IS_PAUSED", false)
    mainLoop:setUpdatesPerSecond(getGameSpeed__ups())
end

local function increaseGameSpeed()
    shiftGameSpeed__ups(10)
    mainLoop:setUpdatesPerSecond(getGameSpeed__ups())
end

local function decreaseGameSpeed()
    shiftGameSpeed__ups(-10)
    mainLoop:setUpdatesPerSecond(getGameSpeed__ups())
end

local function reloadFeatures()
    GAME:sendCommand("PAUSE")
    loadFeatures("features/Reloadable")
    GAME:sendCommand("PLAY")
end

local function quitGame()
    GAME:quit()
end

inputProcessor:addCommand("UP",          VoidFunctionPointer(movePactorUp)) 
inputProcessor:addCommand("DOWN",        VoidFunctionPointer(movePactorDown))
inputProcessor:addCommand("LEFT",        VoidFunctionPointer(movePactorLeft)) 
inputProcessor:addCommand("RIGHT",       VoidFunctionPointer(movePactorRight)) 
inputProcessor:addCommand("PAUSE",       VoidFunctionPointer(pause))
inputProcessor:addCommand("PLAY",        VoidFunctionPointer(play))
inputProcessor:addCommand("GAMESPEED++", VoidFunctionPointer(increaseGameSpeed)) 
inputProcessor:addCommand("GAMESPEED--", VoidFunctionPointer(decreaseGameSpeed))
inputProcessor:addCommand("RELOAD",      VoidFunctionPointer(reloadFeatures))
inputProcessor:addCommand("QUIT",        VoidFunctionPointer(quitGame))