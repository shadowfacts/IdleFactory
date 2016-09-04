package net.shadowfacts.idlefactory.nbt.impl

import net.shadowfacts.idlefactory.nbt.Tag
import net.shadowfacts.idlefactory.nbt.TagHelper
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.IOException
import java.util.*

/**
 * @author shadowfacts
 */
class TagCompound(name: String, var value: MutableMap<String, Tag>) : Tag(name) {

	constructor(name: String) : this(name, LinkedHashMap())

	override fun get(): Map<String, Tag> {
		return value
	}

	fun hasTag(key: String): Boolean {
		return value.containsKey(key)
	}

	operator fun get(key: String): Tag? {
		return value[key]
	}

	operator fun set(key: String, value: Tag) {
		this.value[key] = value
	}

	fun setTag(value: Tag) {
		this[value.name] = value
	}

	fun <T : Tag> getTag(key: String): T? {
		return this[key] as T?
	}

	fun getByte(key: String): Byte {
		return if (hasTag(key)) this[key]!!.get() as Byte else 0
	}

	operator fun set(key: String, value: Byte) {
		this[key] = TagByte(key, value)
	}

	fun getShort(key: String): Short {
		return if (hasTag(key)) this[key]!!.get() as Short else 0
	}

	operator fun set(key: String, value: Short) {
		this[key] = TagShort(key, value)
	}

	fun getInt(key: String): Int {
		return if (hasTag(key)) this[key]!!.get() as Int else 0
	}

	operator fun set(key: String, value: Int) {
		this[key] = TagInt(key, value)
	}

	fun getLong(key: String): Long {
		return if (hasTag(key)) this[key]!!.get() as Long else 0
	}

	operator fun set(key: String, value: Long) {
		this[key] = TagLong(key, value)
	}

	fun getFloat(key: String): Float {
		return if (hasTag(key)) this[key]!!.get() as Float else 0f
	}

	operator fun set(key: String, value: Float) {
		this[key] = TagFloat(key, value)
	}

	fun getDouble(key: String): Double {
		return if (hasTag(key)) this[key]!!.get() as Double else 0.0
	}

	operator fun set(key: String, value: Double) {
		this[key] = TagDouble(key, value)
	}

	fun getByteArray(key: String): ByteArray {
		return if (hasTag(key)) this[key]!!.get() as ByteArray else ByteArray(0)
	}

	operator fun set(key: String, value: ByteArray) {
		this[key] = TagByteArray(key, value)
	}

	fun getString(key: String): String {
		return if (hasTag(key)) this[key]!!.get() as String else ""
	}

	operator fun set(key: String, value: String) {
		this[key] = TagString(key, value)
	}

	fun getTagList(key: String): List<Tag> {
		return if (hasTag(key)) this[key]!!.get() as List<Tag> else ArrayList()
	}

	operator fun set(key: String, value: MutableList<Tag>) {
		this[key] = TagList(key, value)
	}

	fun getTagCompound(key: String): TagCompound? {
		return if (hasTag(key)) this[key]!!.get() as TagCompound else null
	}

	operator fun set(key: String, value: TagCompound) {
		this.value[key] = value
	}

	fun getIntArray(key: String): IntArray {
		return if (hasTag(key)) this[key]!!.get() as IntArray else IntArray(0)
	}

	operator fun set(key: String, value: IntArray) {
		this[key] = TagIntArray(key, value)
	}

	fun getDoubleArray(key: String): DoubleArray {
		return if (hasTag(key)) this[key]!!.get() as DoubleArray else DoubleArray(0)
	}

	operator fun set(key: String, value: DoubleArray) {
		this[key] = TagDoubleArray(key, value)
	}

	fun getFloatArray(key: String): FloatArray {
		return if (hasTag(key)) this[key]!!.get() as FloatArray else FloatArray(0)
	}

	operator fun set(key: String, value: FloatArray) {
		this[key] = TagFloatArray(key, value)
	}

	fun getLongArray(key: String): LongArray {
		return if (hasTag(key)) this[key]!!.get() as LongArray else LongArray(0)
	}

	operator fun set(key: String, value: LongArray) {
		this[key] = TagLongArray(key, value)
	}

	fun getShortArray(key: String): ShortArray {
		return if (hasTag(key)) this[key]!!.get() as ShortArray else ShortArray(0)
	}

	operator fun set(key: String, value: ShortArray) {
		this[key] = TagShortArray(key, value)
	}

	fun getStringArray(key: String): Array<String> {
		return if (hasTag(key)) this[key]!!.get() as Array<String> else Array(0, { "" })
	}

	operator fun set(key: String, value: Array<String>) {
		this[key] = TagStringArray(key, value)
	}

	fun getBoolean(key: String): Boolean {
		return if (hasTag(key)) (this[key]!!.get() as Byte) != 0.toByte() else false
	}

	operator fun set(key: String, value: Boolean) {
		this[key] = TagByte(key, if (value) 1 else 0)
	}

	fun getBooleanArray(key: String): BooleanArray {
		val bytes = getByteArray(key)
		val booleans = BooleanArray(bytes.size)

		for (i in 0.until(bytes.size)) {
			booleans[i] = bytes[i] != 0.toByte()
		}

		return booleans
	}

	operator fun set(key: String, value: BooleanArray) {
		val bytes = ByteArray(value.size)

		for (i in 0.until(value.size)) {
			bytes[i] = if (value[i]) 1 else 0
		}

		this[key] = bytes
	}

	fun remove(key: String) {
		value.remove(key)
	}

	fun keys(): Set<String> {
		return value.keys
	}

	fun values(): Collection<Tag> {
		return value.values
	}

	fun size(): Int {
		return value.size
	}

	fun clear() {
		value.clear()
	}

	override fun write(output: DataOutputStream) {
		values().forEach {
			TagHelper.writeTag(output, it)
		}
		output.writeByte(0)
	}

	override fun read(input: DataInputStream) {
		val tags = ArrayList<Tag>()

		try {
			var tag: Tag?

			while (true) {
				tag = TagHelper.readTag(input)
				if (tag == null) break
				tags.add(tag)
			}
		} catch (e: EOFException) {

			throw IOException("Closing EndTag was not found!")
		}


		tags.forEach { tag -> setTag(tag) }
	}

	override fun clone(): Tag {
		throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}