package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import net.shadowfacts.idlefactory.util.readIntArray
import net.shadowfacts.idlefactory.util.writeIntArray
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagIntArray(name: String, var value: IntArray) : Tag(name) {

	constructor(name: String) : this(name, IntArray(0))

	override fun get(): Any {
		return value.clone()
	}

	override fun write(output: DataOutputStream) {
		output.writeIntArray(value)
	}

	override fun read(input: DataInputStream) {
		value = input.readIntArray()
	}

	override fun clone(): Tag {
		return TagIntArray(name, value)
	}

}