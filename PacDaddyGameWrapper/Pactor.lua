local public = {}

local function new()
    return luajava.newInstance("Engine.Pactor")
end

public.new = new

return public

