local world = GAME:getModifiableWorld()

math.randomseed(os.time())

local function generateCoordinate()
    local ROWS = world:getRows()
    local COLS = world:getCols()
    local row = math.random(ROWS) - 1
    local col = math.random(COLS) - 1
    return row, col
end

local function isEmptyFloor(row, col)
    local tileAttributes = world:getAttributeReaderAtTile(row, col)
    return tileAttributes:getValueOf("NO_PACTOR_AVAILABLE")
end

local function shuffleWalls()

    if math.random(60) == 60 then
        local row, col = generateCoordinate()
        if world:isWall(row, col) then
            world:removeWall(row, col)
        elseif isEmptyFloor(row, col) then
            world:placeWall(row, col)
        end
    end
end

WALL_SHUFFLE_TICK = shuffleWalls