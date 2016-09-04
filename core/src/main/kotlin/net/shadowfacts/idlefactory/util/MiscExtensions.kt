package net.shadowfacts.idlefactory.util

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * @author shadowfacts
 */
fun SpriteBatch.draw(texture: Texture, x: Float, y: Float, rotation: Float) {
	draw(texture, x, y, 0f, 0f, texture.width.toFloat(), texture.height.toFloat(), 1f, 1f, rotation, 0, 0, texture.width, texture.height, false, false)
}

fun DataOutputStream.writeByteArray(value: ByteArray) {
	writeInt(value.size)
	value.forEach {
		writeByte(it.toInt())
	}
}

fun DataInputStream.readByteArray(): ByteArray {
	val size = readInt()
	val array = ByteArray(size)
	for (i in 0.until(size)) {
		array[i] = readByte()
	}
	return array
}

fun DataOutputStream.writeDoubleArray(value: DoubleArray) {
	writeInt(value.size)
	value.forEach {
		writeDouble(it)
	}
}

fun DataInputStream.readDoubleArray(): DoubleArray {
	val size = readInt()
	val array = DoubleArray(size)
	for (i in 0.until(size)) {
		array[i] = readDouble()
	}
	return array
}

fun DataOutputStream.writeFloatArray(value: FloatArray) {
	writeInt(value.size)
	value.forEach {
		writeFloat(it)
	}
}

fun DataInputStream.readFloatArray(): FloatArray {
	val size = readInt()
	val array = FloatArray(size)
	for (i in 0.until(size)) {
		array[i] = readFloat()
	}
	return array
}

fun DataOutputStream.writeIntArray(value: IntArray) {
	writeInt(value.size)
	value.forEach {
		writeInt(it)
	}
}

fun DataInputStream.readIntArray(): IntArray {
	val size = readInt()
	val array = IntArray(size)
	for (i in 0.until(size)) {
		array[i] = readInt()
	}
	return array
}

fun DataOutputStream.writeLongArray(value: LongArray) {
	writeInt(value.size)
	value.forEach {
		writeLong(it)
	}
}

fun DataInputStream.readLongArray(): LongArray {
	val size = readInt()
	val array = LongArray(size)
	for (i in 0.until(size)) {
		array[i] = readLong()
	}
	return array
}

fun DataOutputStream.writeShortArray(value: ShortArray) {
	writeInt(value.size)
	value.forEach {
		writeShort(it.toInt())
	}
}

fun DataInputStream.readShortArray(): ShortArray {
	val size = readInt()
	val array = ShortArray(size)
	for (i in 0.until(size)) {
		array[i] = readShort()
	}
	return array
}

fun DataOutputStream.writeStringArray(value: Array<String>) {
	writeInt(value.size)
	value.forEach {
		writeUTF(it)
	}
}

fun DataInputStream.readStringArray(): Array<String> {
	val size = readInt()
	val array =  Array<String>(size, { "" })
	for (i in 0.until(size)) {
		array[i] = readUTF()
	}
	return array
}
