local args = {...}
local GAME = args[1]

if GAME then
    print("[PASS] The testfeature saw the GAME as an argument.")
else
    print("[FAIL] The testfeature did not see the GAME as an argument.")
end