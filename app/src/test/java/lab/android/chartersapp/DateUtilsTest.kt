import lab.android.chartersapp.charters.presentation.offers.convertToUtcMillis
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class DateUtilsTest {

    @Test
    fun testConvertToUtcMillis() {
        val dateString = "2025-01-20"
        val expectedMillis = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.parse(dateString)?.time ?: 0L

        val actualMillis = convertToUtcMillis(dateString)
        assertEquals(expectedMillis, actualMillis)
    }
}