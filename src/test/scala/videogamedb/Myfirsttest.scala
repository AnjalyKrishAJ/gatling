package videogamedb
import  io.gatling.core.Predef._
import  io.gatling.http.Predef._

class Myfirsttest  extends Simulation{

  // 1. Http Configuration
  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
   .acceptHeader(value = "application/json")

  //2. Scenario Definition
  val scn = scenario(name="My first Test")
    .exec(http(requestName="Get all Games")
    .get("/videogame"))
  //3.Load Scenario

  setUp( scn.inject(atOnceUsers(users = 1)).protocols(httpProtocol)
  )


}
