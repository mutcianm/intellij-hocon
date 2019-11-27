import org.jetbrains.sbtidea.Keys._

intellijPluginName in ThisBuild := "intellij-hocon"
intellijBuild in ThisBuild := "193.4778.7"

val junitInterfaceVersion = "0.11"
val silencerVersion = "1.4.4"

lazy val hocon = project.in(file("."))
  .enablePlugins(SbtIdeaPlugin)
  .dependsOn(hoconCore, hoconJava)

lazy val hoconCore = project.in(file("hocon-core"))
  .enablePlugins(SbtIdeaPlugin)
  .settings(commonSettings: _*)
  .settings(
    intellijInternalPlugins := Seq("properties"),
    patchPluginXml := pluginXmlOptions { xml =>
      xml.version = version.value
    }
  )

lazy val hoconJava = project.in(file("hocon-java"))
  .enablePlugins(SbtIdeaPlugin)
  .settings(commonSettings: _*)
  .settings(
    intellijInternalPlugins := Seq("java", "java-i18n"),
  ).dependsOn(hoconCore)

lazy val runner = createRunnerProject(hocon, "hocon-runner")

lazy val commonSettings = Seq(
  scalaVersion := "2.12.10",
  version := "2019.3.99-SNAPSHOT",
  scalaSource in Compile := baseDirectory.value / "src",
  scalaSource in Test := baseDirectory.value / "test",
  resourceDirectory in Compile := baseDirectory.value / "resources",
  javacOptions in Global ++= Seq("-source", "1.8", "-target", "1.8"),
  scalacOptions in Global ++= Seq(
    "-target:jvm-1.8",
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Xfuture",
    "-Xfatal-warnings",
    "-P:silencer:checkUnused"
  ),
  ideBasePackages := Seq("org.jetbrains.plugins.hocon"),
  ideOutputDirectory in Compile := Some(baseDirectory.value / "out" / "production"),
  ideOutputDirectory in Test := Some(baseDirectory.value / "out" / "test"),
  libraryDependencies ++= Seq(
    "com.novocode" % "junit-interface" % junitInterfaceVersion % Test,
    "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full,
    compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
  )
)