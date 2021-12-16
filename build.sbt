
name := "scala-api-template"
scalaVersion := "2.12.15"

val akkaVersion     = "2.6.17"
val akkaHttpVersion = "10.2.7"
val scalatestVersion = "3.2.10"

val appDependencies = Seq(
    "com.google.inject" % "guice" % "5.0.1",
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "org.typelevel" %% "cats-core" % "2.7.0",
    "org.mongodb.scala" %% "mongo-scala-driver" % "4.4.0"
  )

val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % scalatestVersion % "test, it",
  "org.scalatest" %% "scalatest-flatspec" % scalatestVersion % "test, it",
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "org.scalamock" %% "scalamock" % "5.1.0" % Test,
  "com.github.tomakehurst" % "wiremock" % "2.27.2" % IntegrationTest
)

val loggingDependencies = Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.8",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
  "com.lihaoyi" %% "sourcecode" % "0.2.7"
)

libraryDependencies ++= appDependencies ++ testDependencies ++ loggingDependencies

scalacOptions ++= Seq("-unchecked",
  "-deprecation",
  "-feature",
  "-encoding","UTF-8",
  "-explaintypes",
  "-language:higherKinds",
  "-Ypartial-unification",
  "-Ywarn-infer-any",
  "-Ywarn-unused:imports",
  "-Ywarn-unused:implicits",
  "-Ywarn-unused:locals",
  "-Ywarn-unused:patvars",
  "-Ywarn-unused:privates"
)

dependencyUpdatesFilter -= moduleFilter(organization = "org.scala-lang")

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    coverageMinimumStmtTotal := 1,
    coverageHighlighting := true,
    dependencyUpdatesFailBuild := true,
    coverageFailOnMinimum := true,
    scalastyleFailOnError := true,
    assembly / assemblyJarName := "scala-api-template.jar",
    Compile / scalaSource := baseDirectory.value / "src",
    Compile / resourceDirectory := baseDirectory.value / "conf",
    Test / scalaSource := baseDirectory.value / "test",
    IntegrationTest / scalaSource := baseDirectory.value / "it",
    Test / parallelExecution := false,
    Test / fork := true,
    IntegrationTest / parallelExecution := false,
    IntegrationTest / fork := true
)
  .enablePlugins(SbtPlugin)
