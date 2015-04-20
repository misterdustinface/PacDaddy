local public = {}

local previousLabel

local function displayLabelIfExistsAndNotRepeated()
    if public.label and public.label ~= previousLabel then 
        print(public.label) 
    end
    previousLabel = public.label
end

local function reportTrue()
    displayLabelIfExistsAndNotRepeated()
    print("[PASS]")
end

local function reportFalse()
    displayLabelIfExistsAndNotRepeated()
    print("[FAIL] " .. public.failureMessage)
end

local function test(condition)
    if condition then
        reportTrue()
    else
        reportFalse()
    end
end

public.label = nil
public.failureMessage = ""
public.exists = function(this, entity) test(entity) end
public.equals = function(this, A, B)   test(A == B) end

return public