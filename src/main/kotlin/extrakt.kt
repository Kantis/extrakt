import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

// TODO: Make configurable
val json = Json.Default

sealed interface Action
data class Index(val pos: Int) : Action
data class Field(val name: String) : Action

// $[0]
// $.prop
// $

inline fun <reified T> String.extrakt(path: String): T = json.parseToJsonElement(this).extrakt(path)
inline fun <reified T> JsonElement.extrakt(path: String): T {
    val actions = parseActions(path)
    var p = this

    for (action in actions) {
        p = when (action) {
            is Index -> (p as? JsonArray ?: throw MismatchingTypeException("JsonArray"))[action.pos]
            is Field -> (p as? JsonObject ?: throw MismatchingTypeException("JsonObject"))[action.name]
                ?: throw MissingFieldException("No field by name '${action.name}' in $p")
        }
    }

    return json.decodeFromJsonElement(p)
}

fun parseActions(path: String): List<Action> {
    var i = if (path.startsWith("$")) 1 else 0
    val actions = mutableListOf<Action>()
    fun nextToken() = path.indexOfAny(charArrayOf('.', '[', ']'), i + 1)
        .let {
            if (it == -1) path.length
            else it
        }
        .also { i = it }

    while (i < path.length - 1) {
        when (path[i]) {
            '.' -> actions.add(Field(path.substring(i + 1, nextToken())))
            '[' -> actions.add(Index(path.substring(i + 1, nextToken()).toInt()))
            else -> error("Invalid input $path. (i = $i, actions = $actions)")
        }
    }

    println("Returning actions: $actions")

    return actions
}
