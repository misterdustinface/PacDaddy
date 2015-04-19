
if singletonGameInstance == nil then
  singletonGameInstance = luajava.newInstance("Engine.PacDaddyGame")
end

return singletonGameInstance