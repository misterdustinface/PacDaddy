require("luasrc/VoidFunctionPointer")
local mainLoop = GAME:getModifiableGameLoop()
local world = GAME:getModifiableWorld()

local frienemy = require("PacDaddyGameWrapper/Enemy")
frienemy:setSpawn(5, 6)
world:addPactor("FRIENEMY", frienemy)

local function followPlayer1()
    local player1 = world:getPactor("PLAYER1")
    
    if player1:getRow() < frienemy:getRow() then
        frienemy:performAction("UP")
    elseif player1:getRow() > frienemy:getRow() then
        frienemy:performAction("DOWN")
    elseif player1:getCol() < frienemy:getCol() then
        frienemy:performAction("LEFT")
    elseif player1:getCol() > frienemy:getCol() then
        frienemy:performAction("RIGHT")
    else
        frienemy:performAction("NONE")
    end
    
end

frienemy:learnAction("FOLLOW_PLAYER1", VoidFunctionPointer(followPlayer1))

local function enemyTick()
    frienemy:performAction("FOLLOW_PLAYER1")
    -- TODO
end

mainLoop:addFunction(VoidFunctionPointer(enemyTick))