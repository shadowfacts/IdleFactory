package net.shadowfacts.idlefactory.nbt

/**
 * @author shadowfacts
 */
interface NBTSerializable<T : Tag> {

	fun serializeNBT(tag: T): T

	fun deserializeNBT(tag: T)

}