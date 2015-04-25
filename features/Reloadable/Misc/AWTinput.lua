require("luasrc/tif")
local KEYS = require("AWTLib/AWTKeycodes")

local pressProcessDispatch = {
    [KEYS.UP]    = function() GAME:sendCommand("UP")    end,
    [KEYS.LEFT]  = function() GAME:sendCommand("LEFT")  end,
    [KEYS.DOWN]  = function() GAME:sendCommand("DOWN")  end,
    [KEYS.RIGHT] = function() GAME:sendCommand("RIGHT") end,
    [KEYS.P]     = function() GAME:sendCommand(tif(GAME:getValueOf("IS_PAUSED"), "PLAY", "PAUSE")) end,
    [KEYS.R]     = function() GAME:sendCommand("RELOAD") end,
    [KEYS.Q]     = function() GAME:sendCommand("QUIT")   end,
    [KEYS.ESC]   = function() GAME:sendCommand("QUIT")   end,
}

PRESS_PROCESS_DISPATCH = pressProcessDispatch