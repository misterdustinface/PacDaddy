local public = {}

local function reportTrue()
    print("[PASS]")
end

local function reportFalse()
    print("[FAIL] " .. public.failureMessage)
end

local function test(condition)
    if condition then
        reportTrue()
    else
        reportFalse()
    end
end

public.failureMessage = ""
public.exists = function(this, entity) test(entity) end
public.equals = function(this, A, B)   test(A == B) end

return public