package net.shadowfacts.idlefactory.ecs

import net.shadowfacts.idlefactory.util.Side

/**
 * @author shadowfacts
 */
interface ComponentProvider {

	fun <T> hasComponent(side: Side, clazz: Class<T>): Boolean

	fun <T> getComponent(side: Side, clazz: Class<T>): T?

}