local args = {...}
local GAME = args[1]

local test = require("luasrc/Test")
test.failureMessage = "Could not move the player."

--local tilenames = GAME:getTileNames()
--
--for k,v in ipairs(tilenames) do
--    print(k, v)
--end

--local game = GAME:getSingleton()

test:exists(nil)

--test:equals(0, 1)