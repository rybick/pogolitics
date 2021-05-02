import pogolitcs.format
import kotlin.test.Test
import kotlin.test.assertEquals

class NumberExtensionTest {
    @Test
    fun format() {
        assertEquals("123.45", 123.4511F.format(2))
        assertEquals("0.451", 0.4511F.format(3))
        assertEquals("12", 12F.format(2))
        assertEquals("12.5", 12.5F.format(2))
    }
}
