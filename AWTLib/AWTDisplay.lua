local public = {}

local function init(display, SCREEN_WIDTH, SCREEN_HEIGHT, titleString)

    local JFrame = luajava.bindClass("javax.swing.JFrame")
    local frame  = luajava.newInstance("javax.swing.JFrame")
    local panel  = luajava.newInstance("javax.swing.JPanel")
    panel:setDoubleBuffered(true)
    frame:setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame:add(panel);
    
    display.frame  = frame
    display.panel  = panel
    
    if SCREEN_WIDTH and SCREEN_HEIGHT then
        display:setSize(SCREEN_WIDTH, SCREEN_HEIGHT)
    end
    if titleString then
        display:setTitle(titleString)
    end
    
    display:show()    
end

local function addKeyListener(display, keylistener)
    display.frame:addKeyListener(keylistener)
end

local function setSize(display, SCREEN_WIDTH, SCREEN_HEIGHT)
    if display.frame then
        display.frame:setSize(SCREEN_WIDTH, SCREEN_HEIGHT)
    end
end

local function setTitle(display, titleString)
    display.title  = titleString
    if display.frame then
        display.frame:setTitle(titleString)
    end
end

local function show(display)
    if display.frame then
        display.frame:setVisible(true)
    end
end

local function hide(display)
    if display.frame then
        display.frame:setVisible(false)
    end
end

local function getGraphics(display)
    if display.panel then
        return display.panel:getGraphics()
    end
end

local function getWidth(display)
    if display.frame then
        return display.frame:getWidth()
    else 
        return 0
    end
end

local function getHeight(display)
    if display.frame then
        return display.frame:getHeight()
    else
        return 0
    end
end

public.init = init
public.getGraphics = getGraphics
public.addKeyListener = addKeyListener
public.setSize = setSize
public.setTitle = setTitle
public.show = show
public.hide = hide
public.getWidth = getWidth
public.getHeight = getHeight

return public