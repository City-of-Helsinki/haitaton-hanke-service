package fi.hel.haitaton.hanke

import fi.hel.haitaton.hanke.domain.Hanke
import fi.hel.haitaton.hanke.domain.HankeSearch
import java.time.LocalDate

interface HankeService {

    /**
     * Fetch hanke with hankeTunnus.
     * Either returns the hanke instance, or throws exception.
     * TODO: return type to "Hanke?", and return null if not found, move the exception to controller.
     */
    fun loadHanke(hankeTunnus: String): Hanke

    fun createHanke(hanke: Hanke): Hanke

    fun updateHanke(hanke: Hanke): Hanke

    fun loadAllHanke(): List<Hanke>

    fun loadAllHankeBetweenDates(periodBegin: LocalDate, periodEnd: LocalDate): List<Hanke>

    fun loadAllHankeWithSavetype(saveType: SaveType): List<Hanke>

}
