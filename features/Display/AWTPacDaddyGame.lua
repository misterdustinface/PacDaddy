require("luasrc/tif")
require("luasrc/VoidFunctionPointer")

local Color = luajava.bindClass("java.awt.Color")
local JFrame = luajava.bindClass("javax.swing.JFrame")
local Graphics2D = luajava.bindClass("java.awt.Graphics2D")
local KEYS = require("AWTLib/AWTKeycodes")

local DISPLAY

local inputProcesses = {
    [KEYS.UP]    = function() GAME:sendCommand("UP")    end,
    [KEYS.LEFT]  = function() GAME:sendCommand("LEFT")  end,
    [KEYS.DOWN]  = function() GAME:sendCommand("DOWN")  end,
    [KEYS.RIGHT] = function() GAME:sendCommand("RIGHT") end,
    [KEYS.P]     = function() GAME:sendCommand(tif(GAME:getValueOf("IS_PAUSED"), "PLAY", "PAUSE")) end,
    [KEYS.R]     = function() GAME:sendCommand("RELOAD") end,
    [KEYS.Q]     = function() GAME:sendCommand("QUIT")   end,
    [KEYS.ESC]   = function() GAME:sendCommand("QUIT")   end,
}

local function keypressDispatch(AWTkeyevent)
    local keycode = AWTkeyevent:getKeyCode()
    local inputProcess = inputProcesses[keycode]
    if type(inputProcess) == "function" then
        inputProcess()
    else
        print(keycode)
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

local previousBoard = {}
local function getPreviousTileEnum(row, col)
    if previousBoard[row] == nil then
        return nil
    else 
        return previousBoard[row][col]
    end
end

local borderHeight = 40
local borderWidth  = 40

local previousTileWidth  = 0
local previousTileHeight = 0

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

local function makeGameDrawer()
    local drawer = luajava.newInstance("base.TickingLoop")
    drawer:setUpdatesPerSecond(20)
    local drawfunction = VoidFunctionPointer(drawGame)
    drawer:addFunction(drawfunction)
    return drawer
end

local function create()
    local SCREEN_WIDTH  = 400
    local SCREEN_HEIGHT = 400
    local TITLE = "Pac Daddy"
    
    local inputFactory = require("AWTLib/AWTInputFactory")
    local keylistener = inputFactory.createKeyListener(keypressDispatch)
    
    DISPLAY = require("AWTLib/AWTDisplay")
    DISPLAY:init(SCREEN_WIDTH, SCREEN_HEIGHT, TITLE)
    DISPLAY:addKeyListener(keylistener);
    
    local drawer = makeGameDrawer()
    
    GAME:addComponent("DRAWER", drawer)
end

create()