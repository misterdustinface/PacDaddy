local Color = luajava.bindClass("java.awt.Color")
local JFrame = luajava.bindClass("javax.swing.JFrame")
local Graphics2D = luajava.bindClass("java.awt.Graphics2D")

game = luajava.newInstance("Engine.PacDaddyGame")
boardReader = game:getBoardReader()
inputProcessor = game:getInputProcessor()
attributeReader = game:getGameAttributeReader()

local colormap = {
    ["PLAYER"] = Color.YELLOW,
    ["WALL"]   = Color.WHITE,
    ["FLOOR"]  = Color.BLACK,
}

local inputProcesses = {
    ['w'] = function() inputProcessor:sendCommand("UP")    end,
    ['a'] = function() inputProcessor:sendCommand("LEFT")  end,
    ['s'] = function() inputProcessor:sendCommand("DOWN")  end,
    ['d'] = function() inputProcessor:sendCommand("RIGHT") end,
    ['p'] = function() if attributeReader:getValueOf("IS_PAUSED") then 
                           inputProcessor:sendCommand("PLAY") 
                       else 
                           inputProcessor:sendCommand("PAUSE") 
                       end 
    end, 
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
            local keychar = e:getKeyChar()
            local inputProcess = inputProcesses[keychar]
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
            --local board = boardReader:getTiledBoard()
            --local TILEWIDTH = panel:getWidth() / board[0].length
            --local TILEHEIGHT = panel:getHeight() / board.length
            local g = panel:getGraphics()
            
            g:setColor(Color.BLUE)
            g:fillRect(0,0,panel:getWidth(), panel:getHeight())
            
--            for row = 0, board.length do
--                for col = 0, board[0].length do
--                    local color = getTileColor(board[row][col])
--                    g:setColor(color)
--                    g:fillRect(col * TILEWIDTH, row * TILEHEIGHT, TILEWIDTH, TILEHEIGHT)
--                end
--            end
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