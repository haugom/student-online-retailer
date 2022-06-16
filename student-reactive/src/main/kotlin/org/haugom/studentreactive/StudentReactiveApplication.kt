package org.haugom.studentreactive

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

private val logger = KotlinLogging.logger {}

@SpringBootApplication
class StudentReactiveApplication : CommandLineRunner {
	@Autowired
	@Qualifier("sync")
	lateinit var pageSearcherSync: PageSearcher
	@Autowired
	@Qualifier("async")
	lateinit var pageSearcherAsync: PageSearcher
	@Autowired
	@Qualifier("reactive")
	lateinit var pageSearcherReactive: PageSearcher

	override fun run(vararg args: String?) {
		logger.info { pageSearcherSync.searchPageFor("https://www.vg.no/", "nyhetsdøgnet") }
		logger.info { pageSearcherAsync.searchPageFor("https://www.vg.no/", "nyhetsdøgnet") }
		logger.info { pageSearcherReactive.searchPageFor("https://www.vg.no/", "nyhetsdøgnet") }
	}

}

fun main(args: Array<String>) {
	runApplication<StudentReactiveApplication>(*args)
}
