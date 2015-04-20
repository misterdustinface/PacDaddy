local world = GAME:getModifiableWorld()
local controller = GAME:getModifiablePactorController()

world:addTileType("FLOOR")
world:addTileType("WALL")
world:addTileType("PLAYER")
world:addTileType("ENEMY")

world:loadFromString(
     "1111111111111\n"
  .. "1000000000001"
  .. "1000000000001"
  .. "1000000000001"
  .. "1000110110001"
  .. "1000100010001"
  .. "1000111110001"
  .. "1000000000001"
  .. "1000000000001"
  .. "1000000000001"
  .. "1111111111111\n"
)

local player = require("PacDaddyGameWrapper/Pactor")
player:setSpawn(8, 6)
world:addPactor("PLAYER", player)

controller:setPactor(world:getPactor("PLAYER"))