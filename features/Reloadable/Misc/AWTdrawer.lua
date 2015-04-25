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

local function drawBoard(board)
    local tilenames = GAME:getTileNames()
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
            
                g:setColor(getTileColor("FLOOR"))
                g:fillRect((col-1) * TILEWIDTH + borderWidth, (row-1) * TILEHEIGHT + borderHeight, TILEWIDTH, TILEHEIGHT)
                g:setColor(tileColor)
            
                if tileName == "PICKUP" then
                    local pickupWidth, pickupHeight = TILEWIDTH/4, TILEHEIGHT/4
                    g:fillOval((col-1) * TILEWIDTH + borderWidth + TILEWIDTH/2 - pickupWidth/2, (row-1) * TILEHEIGHT + borderHeight + TILEHEIGHT/2 - pickupHeight/2, pickupWidth, pickupHeight)
                elseif tileName == "PLAYER" then
                    g:fillOval((col-1) * TILEWIDTH + borderWidth, (row-1) * TILEHEIGHT + borderHeight, TILEWIDTH, TILEHEIGHT)
                else
                    g:fillRect((col-1) * TILEWIDTH + borderWidth, (row-1) * TILEHEIGHT + borderHeight, TILEWIDTH, TILEHEIGHT)
                end
                
            end
        end
    end
    
    previousBoard = board
    previousTileWidth = TILEWIDTH
    previousTileHeight = TILEHEIGHT
end

local function drawInfo()
    local g = DISPLAY:getGraphics()

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

local function drawGame()
    local okAccess, board = pcall(GAME.getTiledBoard, GAME)
    if okAccess then drawBoard(board) end
    drawInfo()
end

DRAWGAME = drawGame