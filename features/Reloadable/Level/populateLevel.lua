local world = GAME:getModifiableWorld()

local Player = require("PacDaddyGameWrapper/Player")
local Enemy  = require("PacDaddyGameWrapper/Enemy")
local PointsPickup = require("PacDaddyGameWrapper/PointsPickup")

local player = Player:new()
world:addPactor("PLAYER1", player)
world:setPactorSpawn("PLAYER1", 17, 14)
world:respawnPactor("PLAYER1")

local frienemy = Enemy:new()
world:addPactor("FRIENEMY", frienemy)
world:setPactorSpawn("FRIENEMY", 13, 13)
world:setPactorSpeed("FRIENEMY", 0.5)
world:respawnPactor("FRIENEMY")

local frienemy2 = Enemy:new()
world:addPactor("FRIENEMY2", frienemy2)
world:setPactorSpawn("FRIENEMY2", 13, 14)
world:setPactorSpeed("FRIENEMY2", 0.3)
world:respawnPactor("FRIENEMY2")

--local pickup = PointsPickup:new()
--world:addPactor("PICKUP1", pickup)
--world:setPactorSpawn("PICKUP1", 3, 12)
--world:respawnPactor("PICKUP1")
--
--local pickup2 = PointsPickup:new()
--world:addPactor("PICKUP2", pickup2)
--world:setPactorSpawn("PICKUP2", 4, 12)
--world:respawnPactor("PICKUP2")
--
--local pickup3 = PointsPickup:new()
--world:addPactor("PICKUP3", pickup3)
--world:setPactorSpawn("PICKUP3", 5, 12)
--world:respawnPactor("PICKUP3")

local tilenames = GAME:getTileNames()
local board = GAME:getTiledBoard()
local Pickup = require("PacDaddyGameWrapper/PointsPickup")

for row = 1, board.length do
    for col = 1, board[1].length do
        local tileEnum = board[row][col]
        local tileName = tilenames[tileEnum+1]
        
        if tileName == "FLOOR" then
            local pickup = Pickup:new()
            local pactorName = "PICKUP".."R:"..row.."C:"..col
            world:addPactor(pactorName, pickup)
            world:setPactorSpawn(pactorName, row-1, col-1)
            world:respawnPactor(pactorName)
        end
        
    end
end