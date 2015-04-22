function PactorUpdateFunction(callFunction)
    return luajava.createProxy("InternalInterfaces.PactorUpdateFunction", {
        call = callFunction,
    })
end