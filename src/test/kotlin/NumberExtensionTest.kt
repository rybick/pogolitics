import kotlin.test.Test
import kotlin.test.assertEquals

class NumberExtensionTest {
    @Test
    fun format() {
        assertEquals(123.4511F.format(2), "123.45")
        assertEquals(0.4511F.format(3), "0.451")
        assertEquals(12F.format(2), "12.00")
    }
}