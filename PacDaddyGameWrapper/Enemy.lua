require("PacDaddyGameWrapper/PactorCollisionFunction")
local Pactor = require("PacDaddyGameWrapper/Pactor")

local public = {}

local function new()
    local enemy = Pactor:new()
    enemy:setAttribute("IS_ENEMY", true)
    enemy:setAttribute("VALUE", 200)
    
    local oppositeDirectionTable = {
      ["UP"] = "DOWN",
      ["DOWN"] = "UP",
      ["LEFT"] = "RIGHT",
      ["RIGHT"] = "LEFT",
      ["NONE"] = "NONE",
    }
    
    local function onPactorCollision(otherPactorAttributes)
        if otherPactorAttributes:getValueOf("IS_ENEMY") then
            local myCurrentDirection = enemy:getValueOf("DIRECTION")
            local myOppositeDirection = oppositeDirectionTable[myCurrentDirection]
            enemy:performAction(myOppositeDirection)
        end
    end
    
    enemy:setOnCollisionFunction(PactorCollisionFunction(onPactorCollision))
    return enemy
end

public.new = new

return public