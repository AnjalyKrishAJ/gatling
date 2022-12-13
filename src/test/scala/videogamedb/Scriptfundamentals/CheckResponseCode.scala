package videogamedb.Scriptfundamentals
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.recorder.internal.bouncycastle.oer.its.ieee1609dot2.basetypes.Duration.milliseconds

class CheckResponseCode extends Simulation{
  //1. Http Configuration
  val httpProtocol = http.baseUrl(url= "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")
  //2.Scenario Definition
  val scn = scenario(name="Check Response Code")
    .exec(http(requestName = "Get all games -1")
      .get("/videogame")
      .check(status.is(expected = 200)))
    .pause(5)


    .exec(http(requestName = "Get first game")
      .get("/videogame/1")
      .check(status.in(expected = 200 to 210)))
    .pause(1,10)


    .exec(http(requestName = "Get all games-2")
      .get("/videogame")
      .check(status.not(expected = 400) , status.not(expected = 500)))
    .pause(1000 ,milliseconds)

  //3. Load Scenario
  setUp(scn.inject(atOnceUsers(users = 1)).protocols(httpProtocol)
  )


}
