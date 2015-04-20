function PactorCollisionFunction(callFunction)
    return luajava.createProxy("InternalInterfaces.PactorCollisionFunction", {
        call = callFunction,
    })
end