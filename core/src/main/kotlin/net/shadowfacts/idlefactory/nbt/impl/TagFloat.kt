package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagFloat(name: String, var value: Float) : Tag(name) {

	constructor(name: String) : this(name, 0f)

	override fun get(): Any {
		return value
	}

	override fun write(output: DataOutputStream) {
		output.writeFloat(value)
	}

	override fun read(input: DataInputStream) {
		value = input.readFloat()
	}

	override fun clone(): Tag {
		return TagFloat(name, value)
	}
}