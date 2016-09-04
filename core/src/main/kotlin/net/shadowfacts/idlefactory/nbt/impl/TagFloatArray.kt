package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import net.shadowfacts.idlefactory.util.readFloatArray
import net.shadowfacts.idlefactory.util.writeFloatArray
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagFloatArray(name: String, var value: FloatArray) : Tag(name) {

	constructor(name: String) : this(name, FloatArray(0))

	override fun get(): Any {
		return value.clone()
	}

	override fun write(output: DataOutputStream) {
		output.writeFloatArray(value)
	}

	override fun read(input: DataInputStream) {
		value = input.readFloatArray()
	}

	override fun clone(): Tag {
		return TagFloatArray(name, value)
	}

}