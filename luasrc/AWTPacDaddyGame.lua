local Color = luajava.bindClass("java.awt.Color")
local JFrame = luajava.bindClass("javax.swing.JFrame")
local Graphics2D = luajava.bindClass("java.awt.Graphics2D")
local KEYS = require("luasrc/AWTKeycodes")

game = luajava.newInstance("Engine.PacDaddyGame")
boardReader = game:getBoardReader()
inputProcessor = game:getInputProcessor()
attributeReader = game:getGameAttributeReader()

local colormap = {
    ["PLAYER"] = Color.YELLOW,
    ["WALL"]   = Color.WHITE,
    ["FLOOR"]  = Color.BLACK,
}

local function input(command)
    inputProcessor:sendCommand(command)
end

local function getAttribute(attribute)
    return attributeReader:getValueOf(attribute)
end

local function conditionalInput(condition, commandT, commandF)
    if condition then
        input(commandT)
    else
        input(commandF)
    end
end

local inputProcesses = {
    [KEYS.W] = function() input("UP")    end,
    [KEYS.A] = function() input("LEFT")  end,
    [KEYS.S] = function() input("DOWN")  end,
    [KEYS.D] = function() input("RIGHT") end,
    [KEYS.P] = function() conditionalInput(getAttribute("IS_PAUSED"), "PLAY", "PAUSE") end, 
}

local function getTileColor(tilename)
    local color = colormap[tilename]
    if color == nil then
        color = Color.GRAY
    end
    return color
end

local function makeAWTDisplay(SCREEN_WIDTH, SCREEN_HEIGHT, TitleString)
    local frame = luajava.newInstance("javax.swing.JFrame")
    frame:setSize(SCREEN_WIDTH, SCREEN_HEIGHT)
    frame:setTitle(TitleString);
    frame:setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    local panel = luajava.newInstance("javax.swing.JPanel")
    frame:add(panel);
    frame:setVisible(true);
    return frame, panel
end

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

local function makeGameDrawer(panel)

    local drawer = luajava.newInstance("base.TickingLoop")
    drawer:setUpdatesPerSecond(20)
    
    local drawfunction = luajava.createProxy("functionpointers.VoidFunctionPointer", {
        call = function()
        
            local tilenames = boardReader:getTileNames()
            local board = boardReader:getTiledBoard()
            local TILEWIDTH = panel:getWidth() / board[1].length
            local TILEHEIGHT = panel:getHeight() / board.length
            local g = panel:getGraphics()
            
            for row = 1, board.length do
                for col = 1, board[1].length do
                    local tileEnum = board[row][col]
                    local tileName = tilenames[tileEnum+1]
                    local tileColor = getTileColor(tileName)
                    g:setColor(tileColor)
                    g:fillRect((col-1) * TILEWIDTH, (row-1) * TILEHEIGHT, TILEWIDTH, TILEHEIGHT)
                end
            end
        end
    })
    drawer:addFunction(drawfunction)
    
    return drawer
end

local function create()
    local SCREEN_WIDTH  = 400
    local SCREEN_HEIGHT = 400
    
    local keylistener = makeKeyInputProcessor()
    
    local frame, panel = makeAWTDisplay(SCREEN_WIDTH, SCREEN_HEIGHT, "Pac Daddy")
    frame:addKeyListener(keylistener);
    
    local drawer = makeGameDrawer(panel)
    
    game:addComponent("DRAWER", drawer)
    game:start()
end

create()