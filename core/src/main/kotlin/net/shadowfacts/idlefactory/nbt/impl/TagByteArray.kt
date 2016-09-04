package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import net.shadowfacts.idlefactory.util.readByteArray
import net.shadowfacts.idlefactory.util.writeByteArray
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
class TagByteArray(name: String, var value: ByteArray) : Tag(name) {

	constructor(name: String) : this(name, ByteArray(0))

	override fun get(): Any {
		return value.clone()
	}

	override fun write(output: DataOutputStream) {
		output.writeByteArray(value)
	}

	override fun read(input: DataInputStream) {
		value = input.readByteArray()
	}

	override fun clone(): TagByteArray {
		return TagByteArray(name, value)
	}

}

