local args = {...}
local GAME = args[1]

local test = require("luasrc/Test")
test.failureMessage = "Could not change the player direction."

local result
local player = GAME:getModifiablePactor("PLAYER")

player:performAction("NONE")
result = player:getValueOf("DIRECTION")
test:equals("NONE", result)

player:performAction("RIGHT")
result = player:getValueOf("DIRECTION")
test:equals("RIGHT", result)

test.failureMessage = "Could not control player with PactorController."

local pactorcontroller = GAME:getModifiablePactorController()
pactorcontroller:setPactor(player)
pactorcontroller:sendCommandToPactor("LEFT")
result = player:getValueOf("DIRECTION")
test:equals("LEFT", result)
