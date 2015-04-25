local file = require("luasrc/File")
local world = GAME:getModifiableWorld()

local levelString = file:toString("levels/baselevel.txt")
world:loadFromString(levelString)
--print(levelString)