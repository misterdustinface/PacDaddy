local gameAttributes = GAME:getModifiableAttributes()

local function gameRules()
    if GAME:getValueOf("LIVES") == 0 then
        gameAttributes:setAttribute("LOST_GAME", true)
    end
    
    if GAME:getValueOf("LOST_GAME") == true then
        --print("LOST GAME.")
        --GAME:sendCommand("PAUSE")
        --GAME:quit()
    end
    
end

GAME_RULES_TICK = gameRules