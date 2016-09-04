package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import net.shadowfacts.idlefactory.util.readShortArray
import net.shadowfacts.idlefactory.util.writeShortArray
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagShortArray(name: String, var value: ShortArray) : Tag(name) {

	constructor(name: String) : this(name, ShortArray(0))

	override fun get(): Any {
		return value.clone()
	}

	override fun write(output: DataOutputStream) {
		output.writeShortArray(value)
	}

	override fun read(input: DataInputStream) {
		value = input.readShortArray()
	}

	override fun clone(): Tag {
		return TagShortArray(name, value)
	}

}