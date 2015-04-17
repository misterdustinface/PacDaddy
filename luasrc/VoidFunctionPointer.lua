function newVoidFunctionPointer(callFunction)
    return luajava.createProxy("functionpointers.VoidFunctionPointer", {
        call = callFunction,
    })
end