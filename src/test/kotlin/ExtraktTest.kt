import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class ExtraktTest : FunSpec(
    {
        val jsonTree = Json.Default.parseToJsonElement("""{ "a": [1, 2, 3] }""")

        test("basic") {
            jsonTree.extrakt<Int>("$.a[1]") shouldBe 2
        }

        test("return object") {
            @Serializable
            data class A(val a: List<Int>)

            jsonTree.extrakt<A>("$") shouldBe A(listOf(1, 2, 3))
        }

        test("missing field") {
            shouldThrow<MissingFieldException> {
                jsonTree.extrakt("$.b")
            }.message shouldBe "No field by name 'b' in $jsonTree"
        }

        test("missing inner field, only shows relevant part of tree") {
            val tree = Json.Default.parseToJsonElement(""" {"a" : { "b": { "id": 1, "age": 33} } } """)

            shouldThrow<MissingFieldException> {
                tree.extrakt<String>("$.a.b.name")
            }.message shouldBe """No field by name 'name' in {"id":1,"age":33}"""
        }
    }
)
