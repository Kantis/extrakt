@file:Suppress("unused")

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.exposed(vararg submodules: String): List<Dependency?> =
   submodules.map { submodule ->
      add("implementation", "org.jetbrains.exposed:exposed-$submodule:_")
   }

fun DependencyHandler.testingWithKotestAndMockk() {
   add("testImplementation", "io.kotest:kotest-framework-datatest:_")
   add("testImplementation", "io.kotest:kotest-runner-junit5:_")
   add("testImplementation", "io.kotest:kotest-assertions-core:_")
   add("testImplementation", "io.kotest:kotest-property:_")
   add("testImplementation", "io.mockk:mockk:_")
}

fun DependencyHandler.fuel(
   includeGson: Boolean = true,
   includeCoroutines: Boolean = false,
   serializeWithKotlinx: Boolean = false,
) {
   val groupId = "com.github.kittinunf.fuel"

   add("implementation", "$groupId:fuel:_")

   if (includeGson) {
      add("implementation", "$groupId:fuel-gson:_")
      add("implementation", "com.google.code.gson:gson:_")
   }

   if (serializeWithKotlinx) {
      add("implementation", "$groupId:fuel-kotlinx-serialization:_")
   }

   if (includeCoroutines) {
      add("implementation", "$groupId:fuel-coroutines:_")
   }
}

fun DependencyHandler.logging(includeAwsLambdaLogger: Boolean = false): List<Dependency?> =
   listOfNotNull(
      "org.slf4j:slf4j-api:_",
      "org.slf4j:slf4j-log4j12:_",
      "org.apache.logging.log4j:log4j-core:_",
      if (includeAwsLambdaLogger) "com.amazonaws:aws-lambda-java-log4j:_" else null
   ).map {
      add("implementation", it)
   }

fun DependencyHandler.javaLambda() =
   apply {
      add("implementation", "com.amazonaws:aws-lambda-java-core:_")
      add("implementation", "com.amazonaws:aws-lambda-java-events:_")
   }

fun DependencyHandler.aws(vararg submodules: String): List<Dependency?> =
   submodules.map { submodule ->
      add("implementation", "software.amazon.awssdk:$submodule:_")
   }
      .also {
         platform("software.amazon.awssdk:bom:_")
      }
