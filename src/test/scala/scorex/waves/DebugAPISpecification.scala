package scorex.waves

import org.scalatest.{BeforeAndAfterAll, DoNotDiscover, FunSuite, Matchers}

@DoNotDiscover
class DebugAPISpecification extends FunSuite with Matchers with BeforeAndAfterAll {

  import TestingCommons._

  override def beforeAll: Unit = {
    start()
  }

  override def afterAll: Unit = {
    stop()
  }

  test("/debug/state") {
    val state = getRequest("/debug/state")
    (state \ "3My3KZgFQ3CrVHgz6vGRt8687sH4oAA1qp8").as[Long] should be > 0L
  }

  test("/debug/state/{height}") {
    val state = getRequest("/debug/state/1")
    (state \ "3My3KZgFQ3CrVHgz6vGRt8687sH4oAA1qp8").as[Long] shouldBe 400000000000000L
    (state \ "3NBVqYXrapgJP9atQccdBPAgJPwHDKkh6A8").as[Long] shouldBe 200000000000000L
    (state \ "3N5GRqzDBhjVXnCn44baHcz2GoZy5qLxtTh").as[Long] shouldBe 200000000000000L
    (state \ "3NCBMxgdghg4tUhEEffSXy11L6hUi6fcBpd").as[Long] shouldBe 200000000000000L
    (state \ "3N18z4B8kyyQ96PhN5eyhCAbg4j49CgwZJx").as[Long] shouldBe 9000000000000000L
  }

  test("/debug/info") {
    val info = getRequest("/debug/info")
    (info \ "stateHeight").as[Int] shouldBe application.blockStorage.history.height()
    (info \ "stateHash").asOpt[Int].isDefined shouldBe true
  }

  test("/debug/settings") {
    val info = getRequest("/debug/settings", headers = Map("api_key" -> "test"))
    //    (info \ "p2p" \ "localOnly").as[Boolean] shouldBe true
    //    (info \ "p2p" \ "bindAddress").as[String] shouldBe "127.0.0.1"
    //    (info \ "p2p" \ "port").as[Int] shouldBe 9091
    //    (info \ "rpcPort").as[Int] shouldBe 9092
  }
}