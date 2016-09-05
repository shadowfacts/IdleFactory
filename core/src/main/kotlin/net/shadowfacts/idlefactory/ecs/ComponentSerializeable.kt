package net.shadowfacts.idlefactory.ecs

import net.shadowfacts.idlefactory.nbt.Tag

/**
 * @author shadowfacts
 */
interface ComponentSerializeable<T : Tag> {

	fun serializeNBT(): T

	fun deserializeNBT(tag: T)

}