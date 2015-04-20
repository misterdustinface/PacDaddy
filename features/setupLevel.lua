require("PacDaddyGameWrapper/PactorCollisionFunction")
local file = require("luasrc/File")


local world = GAME:getModifiableWorld()
local controller = GAME:getModifiablePactorController()

world:addTileType("FLOOR")
world:addTileType("WALL")
world:addTileType("PLAYER")
world:addTileType("ENEMY")

local levelString = file:toString("levels/baselevel.txt")

print(levelString)

world:loadFromString(levelString)

local player = require("PacDaddyGameWrapper/Player")
player:setSpawn(8, 6)
world:addPactor("PLAYER1", player)

controller:setPactor(player)

local pickup = require("PacDaddyGameWrapper/Pickup")
pickup:setSpawn(3, 12)
world:addPactor("PICKUP1", pickup)