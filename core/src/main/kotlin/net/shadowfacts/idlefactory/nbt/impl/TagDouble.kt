package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagDouble(name: String, var value: Double) : Tag(name) {

	constructor(name: String) : this(name, 0.0)

	override fun get(): Any {
		return value
	}

	override fun write(output: DataOutputStream) {
		output.writeDouble(value)
	}

	override fun read(input: DataInputStream) {
		value = input.readDouble()
	}

	override fun clone(): Tag {
		return TagDouble(name, value)
	}

}