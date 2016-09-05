package net.shadowfacts.idlefactory.ecs

import net.shadowfacts.idlefactory.nbt.Tag

/**
 * @author shadowfacts
 */
interface ComponentSerializable<T : Tag> {

	fun serializeNBT(): T

	fun deserializeNBT(tag: T)

}