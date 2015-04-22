require("luasrc/VoidFunctionPointer")
local mainLoop = GAME:getModifiableGameLoop()
local world = GAME:getModifiableWorld()

local frienemy = require("PacDaddyGameWrapper/Enemy")
world:addPactor("FRIENEMY", frienemy)
world:setPactorSpawn("FRIENEMY", 5, 6)
world:respawnPactor("FRIENEMY")

local function followPlayer1()
    local player1Pos = {}
    local myPos      = {}
    player1Pos.row   = world:getPactorRow("PLAYER1")
    player1Pos.col   = world:getPactorCol("PLAYER1")
    myPos.row        = world:getPactorRow("FRIENEMY")
    myPos.col        = world:getPactorCol("FRIENEMY")
    
    if player1Pos.row < myPos.row then
        frienemy:performAction("UP")
    elseif player1Pos.row > myPos.row then
        frienemy:performAction("DOWN")
    elseif player1Pos.col < myPos.col then
        frienemy:performAction("LEFT")
    else
        frienemy:performAction("RIGHT")
    end
    
end

frienemy:learnAction("FOLLOW_PLAYER1", VoidFunctionPointer(followPlayer1))

local function enemyTick()
    frienemy:performAction("FOLLOW_PLAYER1")
    -- TODO
end

mainLoop:addFunction(VoidFunctionPointer(enemyTick))