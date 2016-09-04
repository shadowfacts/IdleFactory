package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import net.shadowfacts.idlefactory.util.readDoubleArray
import net.shadowfacts.idlefactory.util.writeDoubleArray
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagDoubleArray(name: String, var value: DoubleArray) : Tag(name) {

	constructor(name: String) : this(name, DoubleArray(0))

	override fun get(): Any {
		return value.clone()
	}

	override fun write(output: DataOutputStream) {
		output.writeDoubleArray(value)
	}

	override fun read(input: DataInputStream) {
		value = input.readDoubleArray()
	}

	override fun clone(): Tag {
		return TagDoubleArray(name, value)
	}

}

