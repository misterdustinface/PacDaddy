local args = {...}
local GAME = args[1]

local test = require("luasrc/Test")
test.failureMessage = "The deeper testfeature did not see the GAME as an argument."
test:exists(GAME)