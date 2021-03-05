import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
	jcenter()
}

plugins {
	kotlin("jvm") version "1.4.31"
	// kotlin("kapt") version "1.4.31"
}

group = "club.geek66.baidu.index"
version = "1.0-SNAPSHOT"

dependencies {
	// kotlin standard
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// kotlinx
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")

	// ktor
	implementation("io.ktor:ktor-server-core:1.5.2")
	implementation("io.ktor:ktor-server-netty:1.5.2")
	implementation("ch.qos.logback:logback-classic:1.2.3")

	testImplementation("org.jetbrains.kotlin:kotlin-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")

	/*implementation("io.arrow-kt:arrow-core:$arrowVersion")
	implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
	kapt("io.arrow-kt:arrow-meta:$arrowVersion")
	kaptTest("io.arrow-kt:arrow-meta:$arrowVersion")*/

	implementation("com.h2database:h2:1.4.197")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "${JavaVersion.VERSION_11}"

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions.jvmTarget = "${JavaVersion.VERSION_11}"