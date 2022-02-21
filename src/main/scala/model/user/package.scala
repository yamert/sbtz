package model

package object user {

  val authorize: String =
    """
      |{
      |  "email": "test00@example.com",
      |  "password": "password"
      |}
      |""".stripMargin.trim

}
