local public = {}

local sys = luajava.bindClass('java.lang.System')
local pathSeparator = sys:getProperty("path.separator")
local fileSeparator = sys:getProperty("file.separator")
local lineSeparator = sys:getProperty("line.separator")

public.path = function(self, ...)
    return table.concat({...}, pathSeparator)
end

public.toString = function(self, filepath)
    local file = io.open(filepath, "rb")
    local filestring = file:read("*a")
    file:close()
    return filestring
end

return public