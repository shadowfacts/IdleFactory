package net.shadowfacts.idlefactory.nbt

import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
abstract class Tag(val name: String) : Cloneable {

	abstract fun get(): Any

	abstract fun write(output: DataOutputStream)

	abstract fun read(input: DataInputStream)

	override abstract fun clone(): Tag
}