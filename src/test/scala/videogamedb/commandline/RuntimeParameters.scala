package videogamedb.commandline

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RuntimeParameters extends Simulation{
  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")

  def USERCOUNT = System.getProperty("USERS", "5").toInt

  def RAMPDURATION = System.getProperty("RAMP_DURATION", "10").toInt

  def TESTDURATION = System.getProperty("TEST_DURATION", "30").toInt

  before {
    println(s"Running test with ${USERCOUNT} users")
    println(s"Ramping users over ${RAMPDURATION} seconds")
    println(s"Total test duration: ${TESTDURATION} seconds")
  }

  def getAllVideogames() = {
    exec(http(requestName = "Get all games")
      .get("/videogame")
      .check(status.is(200)))
  }

  def getSpecificVideogame() = {
    exec(http(requestName = "get Specific game")
      .get("/videogame/1")
      .check(status.in(200 to 210)))
  }

  val scn = scenario(name = "Runtime Parameters")
    .forever {
      exec(getAllVideogames())
      exec(getSpecificVideogame())

    }

  setUp(scn.inject(nothingFor(5),rampUsers(users = USERCOUNT).during(RAMPDURATION))).protocols(httpProtocol).maxDuration(TESTDURATION)

}
