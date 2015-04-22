local file = require("luasrc/File")
local world = GAME:getModifiableWorld()

local levelString = file:toString("levels/baselevel.txt")
print(levelString)

world:loadFromString(levelString)