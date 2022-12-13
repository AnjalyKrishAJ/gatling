package videogamedb.Scriptfundamentals
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Authenticate extends Simulation{

  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")
    .contentTypeHeader(value="application/json")

  def Authenticate()={
    exec(http("authenticate")
    .post("/authenticate")
      .body(StringBody("{\n  \"password\": \"admin\",\n  \"username\": \"admin\"\n}"))
    .check(jsonPath(path = "$.token").saveAs(key = "authorizationtoken")))
  }

  def CreateNewgame()={
    exec(http("create new game")
      .post("/videogame")
      .header(name="Authorization",value="Bearer #{autorizationtoken}")
      .body(StringBody("{\n  \"category\": \"Platform\",\n  \"name\": \"Mario\",\n  \"rating\": \"Mature\",\n  \"releaseDate\": \"2012-05-04\",\n  \"reviewScore\": 85\n}"))
    )
  }


  val scn = scenario(name = "Authenticate and create a new video game")
    .exec(Authenticate())
    .exec(CreateNewgame())

  setUp(scn.inject(atOnceUsers(users = 1))).protocols(httpProtocol)

}
