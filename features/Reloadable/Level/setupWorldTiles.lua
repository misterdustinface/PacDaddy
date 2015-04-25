require("PacDaddyGameWrapper/PactorToTileFunction")

local world = GAME:getModifiableWorld()

--world:addTileType("FLOOR")
--world:addTileType("WALL")
world:addTileType("PLAYER")
world:addTileType("PICKUP")
world:addTileType("ENEMY")

local function PACTOR_TO_TILE_STRING(p)
    if not p then
        return "FLOOR"
    elseif p:getValueOf("IS_PLAYER") then
        return "PLAYER"
    elseif p:getValueOf("IS_ENEMY") then
        return "ENEMY"
    elseif p:getValueOf("IS_PICKUP") then
        return "PICKUP"
    else
        return "FLOOR"
    end
end

world:setPactorToTileFunction(PactorToTileFunction(PACTOR_TO_TILE_STRING))