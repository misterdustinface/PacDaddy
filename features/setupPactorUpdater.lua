require("PacDaddyGameWrapper/PactorUpdateFunction")

local world = GAME:getModifiableWorld()

local function moveUp(pactor)

end

local function moveDown(pactor)

end

local function moveRight(pactor)

end

local function moveLeft(pactor)

end

local getMovementFunction = {
  ["UP"]    = moveUp,
  ["DOWN"]  = moveDown,
  ["LEFT"]  = moveLeft,
  ["RIGHT"] = moveRight,
}

local function movePactorInDirection(pactor, direction)
    local movementFunction = getMovementFunction[direction]
    if type(movementFunction) == "function" then
        movementFunction(pactor)
    end
end

local function PACTOR_UPDATE(pactor)
    local direction = pactor:getValueOf("REQUESTED_DIRECTION")
    movePactorInDirection(pactor, direction)
    world:notifyPactorCollisions(pactor)
end

world:setPactorUpdateFunction(PactorUpdateFunction(PACTOR_UPDATE))