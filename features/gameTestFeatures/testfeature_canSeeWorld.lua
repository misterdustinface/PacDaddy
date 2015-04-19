local args = {...}
local GAME = args[1]

local test = require("luasrc/Test")

local tilenames = GAME:getTileNames()
test.failureMessage = "Could not see world tile names."
test:exists(tilenames)

local tiledboard = GAME:getTiledBoard()
test.failureMessage = "Could not see tiled board."
test:exists(tiledboard)

local modWorld = GAME:getModifiableWorld()
test.failureMessage = "Could not see modifiable world."
test:exists(modWorld)

--for i = 1, tilenames.length do
--    print(tilenames[i])
--end
