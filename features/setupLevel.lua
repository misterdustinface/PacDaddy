--local args = {...}
--local GAME = args[1]

GAME:getModifiableWorld():loadFromString(
     "1111111111111\n"
  .. "1000000000001"
  .. "1000000000001"
  .. "1000000000001"
  .. "1000110110001"
  .. "1000100010001"
  .. "1000111110001"
  .. "1000000000001"
  .. "1000000000001"
  .. "1000000000001"
  .. "1111111111111\n"
)

--return GAME