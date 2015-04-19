local args = {...}
local GAME = args[1]

local test = require("luasrc/Test")
test.failureMessage = "The testfeature did not see the GAME as an argument."
test:exists(GAME)