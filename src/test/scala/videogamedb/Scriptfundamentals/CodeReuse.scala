package videogamedb.Scriptfundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CodeReuse extends Simulation{
  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")
  def getAllVideogames()={
      exec(http(requestName = "Get all games")
      .get("/videogame")
      .check(status.is(200)))
  }

  def getSpecificVideogame()={
    exec(http(requestName = "get Specific game")
    .get("/videogame/1")
    .check(status.in(200 to 210)))
  }

  val scn = scenario(name = "Video game Check response code and extract data from response")
  .exec(getAllVideogames())
  .pause(2)
  .exec(getSpecificVideogame())
  .pause(2)

  setUp(scn.inject(atOnceUsers(users = 1))).protocols(httpProtocol)

}

