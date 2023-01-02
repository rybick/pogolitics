package react

fun <P : Props> fc(block: ChildrenBuilder.(props: P) -> Unit) = FC(block)

val <P : Props> P.attrs get() = this
