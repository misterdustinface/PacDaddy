require("luasrc/VoidFunctionPointer")
GAME = require("PacDaddyGameWrapper/PacDaddyGameWrapper")
local mainLoop = GAME:getModifiableGameLoop()

local function mainLoopAITick()         AI_TICK()            end
local function mainLoopCheckGameRules() GAME_RULES_TICK()    end
local function mainLoopShuffleWalls()   WALL_SHUFFLE_TICK()  end

mainLoop:addFunction(VoidFunctionPointer(mainLoopAITick))
mainLoop:addFunction(VoidFunctionPointer(mainLoopCheckGameRules))
mainLoop:addFunction(VoidFunctionPointer(mainLoopShuffleWalls))