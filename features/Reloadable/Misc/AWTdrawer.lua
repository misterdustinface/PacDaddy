require("luasrc/tif")
local Color = luajava.bindClass("java.awt.Color")

local borderHeight = 40
local borderWidth  = 40

local previousTileWidth  = 0
local previousTileHeight = 0

local previousBoard = {}
local function getPreviousTileEnum(row, col)
    if previousBoard[row] == nil then
        return nil
    else 
        return previousBoard[row][col]
    end
end

local colormap = {
    ["PLAYER"] = Color.YELLOW,
    ["WALL"]   = Color.BLUE,
    ["FLOOR"]  = Color.BLACK,
    ["ENEMY"]  = Color.RED,
    ["PICKUP"] = Color.WHITE,
}

local function getTileColor(tilename)
    local color = colormap[tilename]
    if color == nil then
        color = Color.GRAY
    end
    return color
end

local function drawGame()
    local tilenames = GAME:getTileNames()
    local board = GAME:getTiledBoard()
    local TILEWIDTH = (DISPLAY:getWidth() - 2*borderWidth)/ board[1].length
    local TILEHEIGHT = (DISPLAY:getHeight() - 2*borderHeight) / board.length
    local g = DISPLAY:getGraphics()
    
    for row = 1, board.length do
        for col = 1, board[1].length do
            local previousTileEnum = getPreviousTileEnum(row, col)
            local tileEnum = board[row][col]
            if tileEnum ~= previousTileEnum or TILEWIDTH ~= previousTileWidth or TILEHEIGHT ~= previousTileHeight then
                local tileName = tilenames[tileEnum+1]
                local tileColor = getTileColor(tileName)
                g:setColor(tileColor)
                g:fillRect((col-1) * TILEWIDTH + borderWidth, (row-1) * TILEHEIGHT + borderHeight, TILEWIDTH, TILEHEIGHT)
            end
        end
    end
    
    previousBoard = board
    previousTileWidth = TILEWIDTH
    previousTileHeight = TILEHEIGHT
    
    g:setColor(Color.BLACK)
    g:fillRect(0, 0, DISPLAY:getWidth(), borderHeight)
    g:fillRect(0, 0, borderWidth, DISPLAY:getHeight())
    g:fillRect(DISPLAY:getWidth() - borderWidth, borderHeight, borderWidth, DISPLAY:getHeight() - borderHeight)
    g:fillRect(borderWidth, DISPLAY:getHeight() - borderHeight, DISPLAY:getWidth() - borderWidth, borderHeight)
    
    local upsStr = tif(GAME:getValueOf("IS_PAUSED"), "PAUSED", "UPS: " .. GAME:getValueOf("GAMESPEED__UPS"))
    
    g:setColor(Color.WHITE)
    g:drawString(upsStr, 20, 20)
    
    g:setColor(Color.WHITE)
    g:drawString("LIVES " .. GAME:getValueOf("LIVES"), 80, 20)
    
    g:setColor(Color.WHITE)
    g:drawString("SCORE " .. GAME:getValueOf("SCORE"), 140, 20)
end

DRAWGAME = drawGame