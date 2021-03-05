package fi.hel.haitaton.hanke.tormaystarkastelu

import fi.hel.haitaton.hanke.HankeError
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger { }

@RestController
@RequestMapping("/hankkeet")
class TormaystarkasteluController(@Autowired private val laskentaService: TormaystarkasteluLaskentaService) {

    @GetMapping("/{hankeTunnus}/tormaystarkastelu")
    fun getTormaysTarkastelu(@PathVariable(name = "hankeTunnus") hankeTunnus: String?): ResponseEntity<Any> {
        logger.info {
            "Fetching existing tormaystarkastelu for hanke: $hankeTunnus"
        }

        if (hankeTunnus == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HankeError.HAI1002)
        }

        // call service to get tormaystarkastelu
        val tormaysResults = laskentaService.getTormaystarkastelu(hankeTunnus)
        return if (tormaysResults == null) {
            logger.info { "Tormaystarkastelu does not exist for Hanke $hankeTunnus." }
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(HankeError.HAI1007)
        } else {
            logger.info { "Tormaystarkastelu fetched for Hanke $hankeTunnus." }
            ResponseEntity.status(HttpStatus.OK).body(tormaysResults)
        }
    }

    @PostMapping("/{hankeTunnus}/tormaystarkastelu")
    fun createTormaysTarkasteluForHanke(@PathVariable(name = "hankeTunnus") hankeTunnus: String?): ResponseEntity<Any> {
        logger.info {
            "Creating tormaystarkastelu for hanke: $hankeTunnus"
        }

        if (hankeTunnus == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HankeError.HAI1002)
        }

        // if(hanke.tilat.onTiedotLiikenneHaittaIndeksille)
        // then calculate:
        // - call service to create tormaystarkastelu -> you will get TormaystarkasteluTulos
        // - service has saved tormaystulos to database and
        // saved hanke.onLiikenneHaittaIndeksi=true (or is it services job to do that?)
        // - return hanke with tormaystulos

        // call service TormaystarkasteluLaskentaService
        val hankeWithTormaysResults = laskentaService.calculateTormaystarkastelu(hankeTunnus)

        logger.info { "tormaystarkastelu created for Hanke ${hankeWithTormaysResults.hankeTunnus}." }
        return ResponseEntity.status(HttpStatus.OK).body(hankeWithTormaysResults)
    }
}
