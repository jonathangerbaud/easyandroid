package fr.jonathangerbaud.core.ext

//region collections
fun <E> Collection<E>?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()
fun <E> Collection<E>?.isNotNullOrEmpty() = !isNullOrEmpty()
//endregion collections

//region maps
fun <K, V> Map<K, V>?.isNullOrEmpty() = this == null || this.isEmpty()
fun <K, V> Map<K, V>?.isNotNullOrEmpty() = !isNullOrEmpty()
//endregion maps

//region sets
fun <E> Set<E>?.isNullOrEmpty() = this == null || this.isEmpty()
fun <E> Set<E>?.isNotNullOrEmpty() = !isNullOrEmpty()
//endregion sets

//region lists
fun <E>List<E>?.isNullOrEmpty() = this == null || this.isEmpty()

fun <E>List<E>?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()

fun <E>List<E>?.first(): E? = this?.get(0)
fun <E>List<E>?.last(): E? = this?.get(size - 1)

//region array lists
operator fun<E> ArrayList<E>.plusAssign(obj: E) = add(size, obj)
infix fun <E> ArrayList<E>.addIfNotExist(obj: E) = if (!contains(obj)) add(obj) else false
infix fun <E> ArrayList<E>.removeIfExist(obj: E) = if (contains(obj)) remove(obj) else false