package net.shadowfacts.idlefactory.nbt

/**
 * @author shadowfacts
 */
interface NBTSerializeable<T : Tag> {

	fun serializeNBT(tag: T): T

	fun deserializeNBT(tag: T)

}