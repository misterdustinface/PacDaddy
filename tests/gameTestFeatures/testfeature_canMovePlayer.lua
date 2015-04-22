local test = require("luasrc/Test")
local result

local player = GAME:getModifiablePactor("PLAYER1")
test.label = "Can Move Player"
test.failureMessage = "Could not change the player direction."
player:performAction("LEFT")
result = player:getValueOf("REQUESTED_DIRECTION")
test:equals("LEFT", result)
player:performAction("RIGHT")
result = player:getValueOf("REQUESTED_DIRECTION")
test:equals("RIGHT", result)
player:performAction("UP")
result = player:getValueOf("REQUESTED_DIRECTION")
test:equals("UP", result)
player:performAction("DOWN")
result = player:getValueOf("REQUESTED_DIRECTION")
test:equals("DOWN", result)

test.label = "Can Use PactorController to Move Player"
test.failureMessage = "Could not control player with PactorController."

local pactorcontroller = GAME:getModifiablePactorController()
pactorcontroller:setPactor(player)
pactorcontroller:sendCommandToPactor("LEFT")
result = player:getValueOf("REQUESTED_DIRECTION")
test:equals("LEFT", result)

player:setAttribute("REQUESTED_DIRECTION", "NONE")