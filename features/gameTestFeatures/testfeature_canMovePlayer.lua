local test = require("luasrc/Test")
local result

local player = GAME:getModifiablePactor("PLAYER")
test.label = "Can Move Player"
test.failureMessage = "Could not change the player direction."
player:performAction("NONE")
result = player:getValueOf("DIRECTION")
test:equals("NONE", result)

player:performAction("RIGHT")
result = player:getValueOf("DIRECTION")
test:equals("RIGHT", result)

test.label = "Can Use PactorController to Move Player"
test.failureMessage = "Could not control player with PactorController."

local pactorcontroller = GAME:getModifiablePactorController()
pactorcontroller:setPactor(player)
pactorcontroller:sendCommandToPactor("LEFT")
result = player:getValueOf("DIRECTION")
test:equals("LEFT", result)

player:performAction("NONE")