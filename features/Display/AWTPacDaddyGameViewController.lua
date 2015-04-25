require("luasrc/VoidFunctionPointer")
local JFrame = luajava.bindClass("javax.swing.JFrame")

local function getKeyPressInputProcess(keycode) 
    return PRESS_PROCESS_DISPATCH[keycode]
end

local function keypressDispatch(AWTkeyevent)
    local keycode = AWTkeyevent:getKeyCode()
    local inputProcess = getKeyPressInputProcess(keycode)
    if type(inputProcess) == "function" then
        inputProcess()
    else
        print(keycode)
    end
end

local function gameDrawer()
    DRAWGAME()
end

local function makeGameDrawer()
    local drawer = luajava.newInstance("base.TickingLoop")
    drawer:setUpdatesPerSecond(20)
    local drawfunction = VoidFunctionPointer(gameDrawer)
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