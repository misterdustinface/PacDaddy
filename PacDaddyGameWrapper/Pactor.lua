--require("luasrc/VoidFunctionPointer")
local public = {}

--local function requestDirection(pactor, direction)
--    if pactor:getValueOf("DIRECTION") ~= direction then
--        pactor:setAttribute("REQUESTED_DIRECTION", direction)
--    end
--end
--
--local function learnBasics(pactor)
--    pactor:setAttribute("DIRECTION", "NONE")
--    pactor:setAttribute("REQUESTED_DIRECTION", "NONE")
--    pactor:learnAction("UP",    VoidFunctionPointer(function() requestDirection(pactor, "UP") end))
--    pactor:learnAction("DOWN",  VoidFunctionPointer(function() requestDirection(pactor, "DOWN") end))
--    pactor:learnAction("LEFT",  VoidFunctionPointer(function() requestDirection(pactor, "LEFT") end))
--    pactor:learnAction("RIGHT", VoidFunctionPointer(function() requestDirection(pactor, "RIGHT") end))
--    pactor:learnAction("NONE",  VoidFunctionPointer(function() end))
--end

local function new()
    local pactor = luajava.newInstance("Engine.Pactor")
--    learnBasics(pactor)
    return pactor
end

public.new = new

return public

