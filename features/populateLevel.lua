local world = GAME:getModifiableWorld()

local player = require("PacDaddyGameWrapper/Player")
player:setSpawn(8, 6)
world:addPactor("PLAYER1", player)

local pickup = require("PacDaddyGameWrapper/Pickup")
pickup:setSpawn(3, 12)
world:addPactor("PICKUP1", pickup)