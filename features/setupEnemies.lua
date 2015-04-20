require("luasrc/VoidFunctionPointer")
local mainLoop = GAME:getModifiableGameLoop()
local world = GAME:getModifiableWorld()

local frienemyCount = 0
local count = 0

local function enemyTick()
    if count >= 30 then
      local frienemy = require("PacDaddyGameWrapper/Enemy")
      frienemy:setSpawn(8, 7)
      world:addPactor("FRIENEMY"..frienemyCount, frienemy)
      frienemy:performAction("RIGHT")
      count = 0
      frienemyCount = frienemyCount + 1
    else
      count = count + 1
    end
    print(frienemyCount)
end

mainLoop:addFunction(VoidFunctionPointer(enemyTick))