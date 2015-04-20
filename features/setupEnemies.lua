require("luasrc/VoidFunctionPointer")
local mainLoop = GAME:getModifiableGameLoop()
local world = GAME:getModifiableWorld()

local frienemy = require("PacDaddyGameWrapper/Enemy")
frienemy:setSpawn(5, 6)
world:addPactor("FRIENEMY", frienemy)
frienemy:performAction("UP")

local function enemyTick()
    
end

mainLoop:addFunction(VoidFunctionPointer(enemyTick))