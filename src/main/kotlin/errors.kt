abstract class ExtraktException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)

class MissingFieldException(message: String) : ExtraktException(message)

class IndexOutOfRangeException : ExtraktException("Index out of range")

class MismatchingTypeException(expectedType: String) :
    ExtraktException("Failed to extract bla bla, expected $expectedType but was bla?")
