package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import net.shadowfacts.idlefactory.util.readStringArray
import net.shadowfacts.idlefactory.util.writeStringArray
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagStringArray(name: String, var value: Array<String>) : Tag(name) {

	constructor(name: String) : this(name, emptyArray())

	override fun get(): Any {
		return value.clone()
	}

	override fun write(output: DataOutputStream) {
		output.writeStringArray(value)
	}

	override fun read(input: DataInputStream) {
		value = input.readStringArray()
	}

	override fun clone(): Tag {
		return TagStringArray(name, value)
	}

}