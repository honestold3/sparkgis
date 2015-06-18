import _root_.sbt._
import Keys._
import _root_.sbtassembly.Plugin.AssemblyKeys
import sbtassembly.Plugin._
import AssemblyKeys._

assemblySettings

name := "sparkgis"

version := "1.0"

scalaVersion := "2.10.4"


libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.2.0" % "test" ,
  "org.apache.hadoop" % "hadoop-client" % "2.5.2" ,
  "redis.clients" % "jedis" % "2.1.0",
  "net.debasishg" % "redisclient_2.10" % "2.12",
  "commons-pool" % "commons-pool" %"1.6",
  "net.debasishg" % "redisclient_2.10" %"2.12",
  "org.apache.spark" % "spark-core_2.10" % "1.4.0"
)

resolvers += Resolver.mavenLocal

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

resolvers += Resolver.url("cloudera", url("https://repository.cloudera.com/artifactory/cloudera-repos/."))

resolvers += Resolver.url("MavenOfficial", url("http://repo1.maven.org/maven2"))

resolvers += Resolver.url("conjars", url("http://conjars.org/repo"))

resolvers += Resolver.url("jboss", url("http://repository.jboss.org/nexus/content/groups/public-jboss"))

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
{
  case PathList("com", "esotericsoftware", "minlog", xs @ _*) => MergeStrategy.first
  case PathList("org", "jboss","netty", xs @ _*) => MergeStrategy.last
  case PathList("com", "google","common", xs @ _*) => MergeStrategy.last
  case PathList("com", "codahale", xs @ _*)=> MergeStrategy.first
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
  case PathList("org", "apache", xs @ _*)=> MergeStrategy.last
  case PathList("org", "eclipse", xs @ _*) => MergeStrategy.first
  case PathList("akka",  xs @ _*)=> MergeStrategy.first
  case PathList("parquet",  xs @ _*)=> MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "jboss-beans.xml" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "pom.properties" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "pom.xml" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "overview.html" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "plugin.xml" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "parquet.thrift" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "ECLIPSEF.RSA" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "mailcap" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "plugin.properties" => MergeStrategy.filterDistinctLines
  case "application.conf" => MergeStrategy.concat
  case "unwanted.txt"     => MergeStrategy.discard
  case x => old(x)
}
}

mainClass in assembly := Some("cn.com.gis.etl.CaculateGis")

jarName in assembly := "run.jar"