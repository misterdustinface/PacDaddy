local function sortFilesByLoadPriority(filelist)
    -- TODO SORT
end

-- Because table.insert(A, table.unpack(B)) is fucked up in luaj 
local function mergetables(A, B)
    for index = 1, #B do
        table.insert(A, B[index])
    end
end

local function getAbsoluteFiles(pathname)
    local absfiles = {}
    local filepath = luajava.newInstance("java.io.File", pathname)
    
    if filepath:isDirectory() and not filepath:isHidden() then
        local files = filepath:listFiles()
        for i = 1, files.length do
            local subfile = files[i]
            local subfilepath = subfile:getPath()
            local recursiveResult = getAbsoluteFiles(subfilepath)
            mergetables(absfiles, recursiveResult)
        end
    elseif filepath:isFile() and not filepath:isHidden() then
        table.insert(absfiles, pathname)
    end
    
    return absfiles
end

function loadFeatures(featuresFolderFilePath)
    local files = getAbsoluteFiles(featuresFolderFilePath)
    sortFilesByLoadPriority(files)
    for _, filename in ipairs(files) do
        print(filename)
        local chunk = assert(loadfile(filename), "Failed to load " .. filename .. " as a lua chunk")
        chunk()
    end
end

function setGamePath(gameWrapperFilePath)
    GAME = require(gameWrapperFilePath)
end