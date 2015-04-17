local args = {...}
local GAME = args[1]

require("luasrc/tif")
require("luasrc/VoidFunctionPointer")

local Color = luajava.bindClass("java.awt.Color")
local JFrame = luajava.bindClass("javax.swing.JFrame")
local Graphics2D = luajava.bindClass("java.awt.Graphics2D")
local KEYS = require("AWTfeatures/AWTKeycodes")

local inputProcesses = {
    [KEYS.UP]    = function() GAME:sendCommand("UP")    end,
    [KEYS.LEFT]  = function() GAME:sendCommand("LEFT")  end,
    [KEYS.DOWN]  = function() GAME:sendCommand("DOWN")  end,
    [KEYS.RIGHT] = function() GAME:sendCommand("RIGHT") end,
    [KEYS.P]     = function() GAME:sendCommand(tif(GAME:getValueOf("IS_PAUSED"), "PLAY", "PAUSE")) end, 
}

local function makeKeyInputProcessor()
    local keylistener = luajava.createProxy("java.awt.event.KeyListener", {
        keyPressed = function(e)
            local keycode = e:getKeyCode()
            local inputProcess = inputProcesses[keycode]
            if type(inputProcess) == "function" then
                inputProcess()
            end
        end,
        keyReleased = function(this, e) end,
        keyTyped = function(this, e) end,
    })
    
    return keylistener
end

local colormap = {
    ["PLAYER"] = Color.YELLOW,
    ["WALL"]   = Color.BLUE,
    ["FLOOR"]  = Color.BLACK,
}

local function getTileColor(tilename)
    local color = colormap[tilename]
    if color == nil then
        color = Color.GRAY
    end
    return color
end

local function makeGameDrawer(display)

    local drawer = luajava.newInstance("base.TickingLoop")
    drawer:setUpdatesPerSecond(20)
    
    local drawfunction = newVoidFunctionPointer(
        function()
            local tilenames = GAME:getTileNames()
            local board = GAME:getTiledBoard()
            local TILEWIDTH = display:getWidth() / board[1].length
            local TILEHEIGHT = display:getHeight() / board.length
            local g = display:getGraphics()
            
            for row = 1, board.length do
                for col = 1, board[1].length do
                    local tileEnum = board[row][col]
                    local tileName = tilenames[tileEnum+1]
                    local tileColor = getTileColor(tileName)
                    g:setColor(tileColor)
                    g:fillRect((col-1) * TILEWIDTH, (row-1) * TILEHEIGHT, TILEWIDTH, TILEHEIGHT)
                end
            end
            
            local upsStr = tif(GAME:getValueOf("IS_PAUSED"), "PAUSED", "UPS: " .. GAME:getValueOf("GAMESPEED__UPS"))
            
            g:setColor(Color.WHITE)
            g:drawString(upsStr, 20, 20)
            
            g:setColor(Color.WHITE)
            g:drawString("LIVES " .. GAME:getValueOf("LIVES"), 80, 20)
            
            g:setColor(Color.WHITE)
            g:drawString("SCORE " .. GAME:getValueOf("SCORE"), 140, 20)

        end
    )
    
    drawer:addFunction(drawfunction)
    
    return drawer
end

local function create()
    local SCREEN_WIDTH  = 400
    local SCREEN_HEIGHT = 400
    local TITLE = "Pac Daddy"
    
    local keylistener = makeKeyInputProcessor()
    
    local display = require("AWTfeatures/AWTDisplay")
    display:init(SCREEN_WIDTH, SCREEN_HEIGHT, TITLE)
    display:addKeyListener(keylistener);
    
    local drawer = makeGameDrawer(display)
    
    GAME:addComponent("DRAWER", drawer)
    GAME:start()
end

create()