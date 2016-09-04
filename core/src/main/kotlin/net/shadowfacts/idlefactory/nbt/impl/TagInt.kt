package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagInt(name: String, var value: Int) : Tag(name) {

	constructor(name: String) : this(name, 0)

	override fun get(): Any {
		return value
	}

	override fun write(output: DataOutputStream) {
		output.writeInt(value)
	}

	override fun read(input: DataInputStream) {
		value = input.readInt()
	}

	override fun clone(): Tag {
		return TagInt(name, value)
	}

}