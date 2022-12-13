package videogamedb.Scriptfundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class loop extends Simulation{
  //1.Http Configuration
  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")
  def getAllVideogames()= {
    repeat(times = 3) {
      exec(http(requestName = "Get all games")
        .get("/videogame")
        .check(status.is(200)))
    }
  }

  def getSpecificVideogame()= {
    repeat(times = 5, counterName = "counter") {
      exec(http(requestName = "get #{counter} game")
        .get("/videogame/#{counter}")
        .check(status.in(200 to 210)))
    }
  }
//2. Scenario definition
  val scn = scenario(name = "Video game Check response code and extract data from response")
    .exec(getAllVideogames())
    .pause(2)
    .exec(getSpecificVideogame())
    .pause(2)
    .repeat(times = 2){
      getAllVideogames()
    }
 // 3. Load Scenario
  setUp(scn.inject(atOnceUsers(users = 1))).protocols(httpProtocol)

}
