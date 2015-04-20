function PactorToTileFunction(callFunction)
    return luajava.createProxy("InternalInterfaces.PactorToTileFunction", {
        getTileName = callFunction,
    })
end