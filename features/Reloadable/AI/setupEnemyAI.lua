local world = GAME:getModifiableWorld()

local frienemy = world:getPactor("FRIENEMY")

local function followPlayer1()
    local player1Pos = { row = world:getRowOf("PLAYER1"),  col = world:getColOf("PLAYER1") }
    local myPos      = { row = world:getRowOf("FRIENEMY"), col = world:getColOf("FRIENEMY") }
    
    if player1Pos.row < myPos.row and not world:isWall(myPos.row-1, myPos.col) then
        frienemy:performAction("UP")
    elseif player1Pos.row > myPos.row and not world:isWall(myPos.row+1, myPos.col) then
        frienemy:performAction("DOWN")
    elseif player1Pos.col < myPos.col and not world:isWall(myPos.row, myPos.col-1) then
        frienemy:performAction("LEFT")
    elseif player1Pos.col > myPos.col and not world:isWall(myPos.row, myPos.col+1) then
        frienemy:performAction("RIGHT")
    end
end

frienemy:learnAction("FOLLOW_PLAYER1", VoidFunctionPointer(followPlayer1))

local function enemyTick()
    frienemy:performAction("FOLLOW_PLAYER1")
    -- TODO
end

AI_TICK = enemyTick