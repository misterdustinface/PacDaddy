local args = {...}
local GAME_WRAPPER_FILE_PATH = args[1]
local FEATURES_FOLDER = args[2]

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

local function loadFeatures(featuresFolder) 
    local GAME = require(GAME_WRAPPER_FILE_PATH)
    local files = getAbsoluteFiles(featuresFolder)
    for _, filename in ipairs(files) do
        local chunk = assert(loadfile(filename), "Failed to load " .. filename .. " as a chunk")
        chunk(GAME)
    end
end

loadFeatures(FEATURES_FOLDER)