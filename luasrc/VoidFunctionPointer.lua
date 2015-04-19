function VoidFunctionPointer(callFunction)
    return luajava.createProxy("functionpointers.VoidFunctionPointer", {
        call = callFunction,
    })
end