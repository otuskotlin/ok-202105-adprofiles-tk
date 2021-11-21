import Ad.toAd
import Ad.toAdDto
import model.Ad
import ru.tk.adprofiles.kmp.transport.models.AdDto
import java.time.Clock
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import kotlin.test.Test
import kotlin.test.assertEquals

class AdMapperTest {
    protected val clock= Clock.fixed(Instant.parse("2019-07-29T10:15:30.00Z"), ZoneId.of("Europe/Moscow"))

    @Test
    fun adMapTest(){
        val adDto = AdDto(
            "id",
            "title",
            "description",
                    mutableListOf("photo"),
            "2019-07-29T10:15:30Z",
            "2019-07-29T10:15:30Z",
        )
        val ad = Ad(
            "id",
            "title",
            "description",
                    mutableListOf("photo"),
            OffsetDateTime.parse("2019-07-29T10:15:30.00Z"),
            OffsetDateTime.parse("2019-07-29T10:15:30.00Z"),
        )

        val adEx = adDto.toAd()
        val adDtoEx = ad.toAdDto()

        assertEquals(adEx, ad)
        assertEquals(adDtoEx, adDto)
    }
}
