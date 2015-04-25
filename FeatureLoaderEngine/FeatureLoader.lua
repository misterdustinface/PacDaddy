local gatherFeatureFiles

-- Because table.insert(A, table.unpack(B)) is fucked up in luaj 
local function mergetables(A, B)
    for index = 1, #B do
        table.insert(A, B[index])
    end
end

local function concatPathToFile(filepath, file)
    return filepath .. "/" .. file
end

local function concatPathToFiles(filepath, files)
    for index = 1, #files do
        files[index] = concatPathToFile(filepath, files[index])
    end
    return files
end

local function isVisibleDirectory(filepath)
    local javaFile = luajava.newInstance("java.io.File", filepath)
    return javaFile:isDirectory() and not javaFile:isHidden()
end

local function isVisibleFile(filepath)
    local javaFile = luajava.newInstance("java.io.File", filepath)
    return javaFile:isFile() and not javaFile:isHidden()
end

local function getFilePathsInDirectory(directoryPath)
    local filepaths = {}
    local javaDir = luajava.newInstance("java.io.File", directoryPath)
    local javaFiles = javaDir:listFiles()
    for i = 1, javaFiles.length do
        filepaths[i] = javaFiles[i]:getPath()
    end
    return filepaths
end

local function gatherFeatureFilesForPaths(paths)
    local files = {}
    for i = 1, #paths do
        local recursiveResult = gatherFeatureFiles(paths[i])
        mergetables(files, recursiveResult)
    end
    return files
end

local function gatherFeatureFilesFromDirectory(directoryPath)
    local orderingFilePath = concatPathToFile(directoryPath, "_order.lua")
    local hasOrderingFile, ordering = pcall(dofile, orderingFilePath)
    
    if hasOrderingFile then
        local orderedPaths = concatPathToFiles(directoryPath, ordering)
        return gatherFeatureFilesForPaths(orderedPaths)
    else 
        local filePaths = getFilePathsInDirectory(directoryPath)
        return gatherFeatureFilesForPaths(filePaths)
    end
end

function gatherFeatureFiles(filepath)
    local files = {}
    if isVisibleDirectory(filepath) then
        mergetables(files, gatherFeatureFilesFromDirectory(filepath))
    elseif isVisibleFile(filepath) then
        table.insert(files, filepath)
    end
    return files
end

function loadFeatures(featuresFolderFilePath)
    local files = gatherFeatureFiles(featuresFolderFilePath)
    for _, filename in ipairs(files) do
        if DISPLAY_LOADED_FILES then print(filename) end
        local chunk = assert(loadfile(filename), "Failed to load " .. filename .. " as a lua chunk")
        chunk()
    end
end

function displayLoadedFiles()
    DISPLAY_LOADED_FILES = true
end

function setApplication(appObject)
    if APPLICATION == nil then
        APPLICATION = appObject
    end
end