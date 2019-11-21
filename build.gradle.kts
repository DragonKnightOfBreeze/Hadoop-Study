plugins {
	kotlin("jvm") version "1.3.60"
}

group = "com.windea"
version = "1.0.0"

repositories {
	maven("maven.aliyun.com/nexus/content/groups/public/")
	jcenter()
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib"))
	testImplementation(kotlin("test-junit"))
	
	implementation("org.apache.hadoop:hadoop-common:3.2.0")
	implementation("org.apache.hadoop:hadoop-hdfs:3.2.0")
	implementation("org.apache.hadoop:hadoop-mapreduce-client-core:3.2.0")
	implementation("org.apache.hadoop:hadoop-yarn-api:3.2.0")
}
