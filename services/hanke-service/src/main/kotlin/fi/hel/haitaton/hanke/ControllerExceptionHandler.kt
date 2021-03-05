package fi.hel.haitaton.hanke

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger { }

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(HankeNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun hankeNotFound(ex: HankeNotFoundException): HankeError {
        logger.warn {
            "Hanke ${ex.hankeTunnus} not found"
        }
        return HankeError.HAI1001
    }

    @ExceptionHandler(HankeYhteystietoNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun hankeYhteystietoNotFound(ex: HankeYhteystietoNotFoundException): HankeError {
        logger.warn {
            "Yhteystieto for Hanke ${ex.hankeid} not found"
        }
        return HankeError.HAI1020
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun illegalArgumentException(ex: IllegalArgumentException): HankeError {
        logger.error(ex) {
            ex.message
        }
        return HankeError.HAI0003
    }

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun throwable(ex: Throwable): HankeError {
        logger.error(ex) {
            ex.message
        }
        return HankeError.HAI0002
    }
}
