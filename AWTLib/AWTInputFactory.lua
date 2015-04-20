local public = {}

local function makeKeyInputProcessor(pressFunction, releaseFunction, typedFunction)
    local keylistener = luajava.createProxy("java.awt.event.KeyListener", {
        keyPressed  = pressFunction,
        keyReleased = releaseFunction,
        keyTyped    = typedFunction,
    })
    return keylistener
end

public.createKeyListener = makeKeyInputProcessor

return public