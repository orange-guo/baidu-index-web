import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	kotlin("jvm") version "1.4.31"
	kotlin("plugin.serialization") version "1.4.31"
	// kotlin("kapt") version "1.4.31"
}

group = "club.geek66.baidu.index"
version = "1.0-SNAPSHOT"

application {
	mainClass.set("io.ktor.server.netty.EngineMain")
}

buildscript {
	repositories {
		maven { setUrl("http://maven.aliyun.com/nexus/content/groups/public/") }
		maven { setUrl("https://kotlin.bintray.com/kotlinx") }
		jcenter()
	}
}

repositories {
	maven { setUrl("http://maven.aliyun.com/nexus/content/groups/public/") }
	maven { setUrl("https://kotlin.bintray.com/kotlinx") }
	maven { setUrl("https://oss.sonatype.org/content/repositories/snapshots") }
	jcenter()
}

val kotlinxSerializationVersion: String by project
val kotlinxCoroutinesVersion: String by project
val ktorVersion: String by project
val h2Version: String by project
val javaVersion: String by project
val junitVersion: String by project
val kotestVersion: String by project

dependencies {
	// kotlin standard
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.jetbrains.kotlin:kotlin-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

	// kotlinx
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxSerializationVersion")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")

	// ktor
	implementation("io.ktor:ktor-server-core:$ktorVersion")
	implementation("io.ktor:ktor-serialization:$ktorVersion")
	implementation("io.ktor:ktor-server-netty:$ktorVersion")
	testImplementation("io.ktor:ktor-server-tests:$ktorVersion")

	// kotest
	testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
	testImplementation("io.kotest:kotest-property:$kotestVersion")

	testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
	implementation("com.h2database:h2:$h2Version")
	implementation("ch.qos.logback:logback-classic:1.2.3")

	/*implementation("io.arrow-kt:arrow-core:$arrowVersion")
	implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
	kapt("io.arrow-kt:arrow-meta:$arrowVersion")
	kaptTest("io.arrow-kt:arrow-meta:$arrowVersion")*/
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = javaVersion

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions.jvmTarget = javaVersion