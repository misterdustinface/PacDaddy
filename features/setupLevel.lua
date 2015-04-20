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

world:addPactor("PLAYER", require("PacDaddyGameWrapper/Pactor"))
world:getPactor("PLAYER"):getTileCoordinate().row = 3
world:getPactor("PLAYER"):getTileCoordinate().col = 3

controller:setPactor(world:getPactor("PLAYER"))