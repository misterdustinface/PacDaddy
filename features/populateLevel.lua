local world = GAME:getModifiableWorld()

local player = require("PacDaddyGameWrapper/Player")
world:addPactor("PLAYER1", player)
world:setPactorSpawn("PLAYER1", 8, 6)
world:respawnPactor("PLAYER1")

local pickup = require("PacDaddyGameWrapper/Pickup")
world:addPactor("PICKUP1", pickup)
world:setPactorSpawn("PICKUP1", 3, 12)
world:respawnPactor("PICKUP1")