local args = {...}
local GAME_WRAPPER_FILE_PATH    = args[1]
local FEATURES_FOLDER_FILE_PATH = args[2]

local function sortFilesByLoadPriority(filelist)
    -- TODO SORT
end

local function getAbsoluteFiles(pathname)
    --print("PATH: " .. pathname)
    local absfiles = {}
    local filepath = luajava.newInstance("java.io.File", pathname)
    
    if filepath:isDirectory() and not filepath:isHidden() then
        local files = filepath:listFiles()
        for i = 1, files.length do
            local subfile = files[i]
            local subfilepath = subfile:getPath()
            local recursiveResult = getAbsoluteFiles(subfilepath)
            table.insert(absfiles, table.unpack(recursiveResult))
        end
    elseif filepath:isFile() and not filepath:isHidden() then
        table.insert(absfiles, pathname)
    end
    
    return absfiles
end

local function loadFeatures(featuresFolderFilePath) 
    local GAME = require(GAME_WRAPPER_FILE_PATH)
    local files = getAbsoluteFiles(featuresFolderFilePath)
    sortFilesByLoadPriority(files)
    for _, filename in ipairs(files) do
        local chunk = assert(loadfile(filename), "Failed to load " .. filename .. " as a lua chunk")
        assert(pcall(chunk, GAME), "Call to lua chunk " .. filename .. " failed!")
    end
end

loadFeatures(FEATURES_FOLDER_FILE_PATH)