package videogamedb.Scriptfundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._


class CheckResponseBodyandExtract extends Simulation{

  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")

  val scn = scenario(name = "Video game Check response code and extract data from response")
    .exec(http(requestName = "Get a game").get("/videogame/1")
      check(jsonPath(path = "$.name").is("Resident Evil 4")))
    .pause(1, 10)

    .exec(http(requestName = "Get all games").get("/videogame")
      check (jsonPath(path = "$[0].id").saveAs(key = "gameId")))
    .pause(1, 10)

    .exec(http(requestName = "Get a game").get("/videogame/#{gameId}")
      check (jsonPath(path = "$.name").is ("Resident Evil 4")))
    .pause(1, 10)

  setUp(scn.inject(atOnceUsers(users = 1))).protocols(httpProtocol)

}
