local world = GAME:getModifiableWorld()

local player = require("PacDaddyGameWrapper/Player")
world:addPactor("PLAYER1", player)
world:setPactorSpawn("PLAYER1", 17, 14)
world:respawnPactor("PLAYER1")

local pickup = require("PacDaddyGameWrapper/Pickup")
world:addPactor("PICKUP1", pickup)
world:setPactorSpawn("PICKUP1", 3, 12)
world:respawnPactor("PICKUP1")

local frienemy = require("PacDaddyGameWrapper/Enemy")
world:addPactor("FRIENEMY", frienemy)
world:setPactorSpawn("FRIENEMY", 14, 14)
world:setPactorSpeed("FRIENEMY", 0.5)
world:respawnPactor("FRIENEMY")