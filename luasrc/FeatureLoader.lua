local FEATURES_FOLDERS = {...}

local function getAbsoluteFiles(pathname)
    local absfiles = {}
    local filepath = luajava.newInstance("java.io.File", pathname)
    
    if filepath:isDirectory() and not filepath:isHidden() then
        local files = filepath:listFiles()
        for i = 1, files.length do
            local file = files[i]
            local recursiveResult = getAbsoluteFiles(file:getPath())
            table.insert(absfiles, table.unpack(recursiveResult))
        end
    elseif filepath:isFile() and not filepath:isHidden() then
        table.insert(absfiles, pathname)
    end
    
    return absfiles
end

local function loadFeatures(featuresFolders) 
    local GAME = require("luasrc/PacDaddyGameWrapper")
    for _, path in ipairs(featuresFolders) do
        local files = getAbsoluteFiles(path)
        for _, filename in ipairs(files) do
            local chunk = assert(loadfile(filename), "Failed to load " .. filename .. " as a chunk")
            chunk(GAME)
        end
    end
end

loadFeatures(FEATURES_FOLDERS)