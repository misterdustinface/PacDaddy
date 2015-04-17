local public = {}

local sys = luajava.bindClass('java.lang.System')
local pathSeparator = sys:getProperty("path.separator")
local fileSeparator = sys:getProperty("file.separator")
local lineSeparator = sys:getProperty("line.separator")

public.path = function(self, ...)
    return table.concat({...}, pathSeparator)
end

return public