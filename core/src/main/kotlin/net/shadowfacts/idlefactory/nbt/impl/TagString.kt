package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagString(name: String, var value: String) : Tag(name) {

	constructor(name: String) : this(name, "")

	override fun get(): Any {
		return value
	}

	override fun write(output: DataOutputStream) {
		output.writeUTF(value)
	}

	override fun read(input: DataInputStream) {
		value = input.readUTF()
	}

	override fun clone(): Tag {
		return TagString(name, value)
	}

}