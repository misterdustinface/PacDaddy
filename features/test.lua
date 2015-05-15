print("CAN SEE FEATURES")

print("COMPONENTS OF THE APPLICATION ARE:")
local components = APPLICATION:getComponentNames()
for i = 1, components.length do
    print(components[i])
end

print("ATTEMPT TO QUIT APPLICATION")

APPLICATION:quit()
