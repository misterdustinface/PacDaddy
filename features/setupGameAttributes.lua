local gameAttributes = GAME:getModifiableAttributes()

gameAttributes:setAttribute("SCORE", 0)
gameAttributes:setAttribute("LIVES", 3)
gameAttributes:setAttribute("GAMESPEED__UPS", 15)
gameAttributes:setAttribute("IS_PAUSED", true)
gameAttributes:setAttribute("LOST_GAME", false)