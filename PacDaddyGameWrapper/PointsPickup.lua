require("PacDaddyGameWrapper/PactorCollisionFunction")
local Pactor = require("PacDaddyGameWrapper/Pactor")

local public = {}

local function new()
    local pickup = Pactor:new()
    pickup:setAttribute("IS_PICKUP", true)
    pickup:setAttribute("VALUE", 1)
    
    --pickup:forgetAction("LEFT")
    --pickup:forgetAction("DOWN")
    --pickup:forgetAction("UP")
    --pickup:forgetAction("RIGHT")
    
    local function onPactorCollision(otherPactorAttributes)
        if otherPactorAttributes:getValueOf("IS_PLAYER") then
            local gameAttributes = GAME:getModifiableAttributes()
            gameAttributes:setAttribute("SCORE", gameAttributes:getValueOf("SCORE") + pickup:getValueOf("VALUE"))
            local world = GAME:getModifiableWorld()
            local myName = pickup:getValueOf("NAME")
            world:removePactor(myName)
        end
    end
    
    pickup:setOnCollisionFunction(PactorCollisionFunction(onPactorCollision))
    return pickup
end

public.new = new

return public