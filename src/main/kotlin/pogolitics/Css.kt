package pogolitics

import csstype.Cursor
import csstype.FontFamily

// This is a workaround as using Cursor.pointer currently throws a js compilation error,
// as the generated code includes { ..., default: default, ...}, while `deafult` is a JS keyword
// remove this and just use Cursor.pointer when they fix it.
val pointer = "pointer".unsafeCast<Cursor>()
val default = "default".unsafeCast<Cursor>()

val arial = "Arial".unsafeCast<FontFamily>()